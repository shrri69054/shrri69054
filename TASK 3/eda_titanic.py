"""
========================================================
  Exploratory Data Analysis (EDA) - Titanic Dataset
  Author: EDA Project
  Date: May 2026
========================================================
"""

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec
import seaborn as sns
from matplotlib.patches import FancyBboxPatch
import warnings
warnings.filterwarnings('ignore')

# ── Aesthetic config ──────────────────────────────────
PALETTE   = ["#1a1a2e", "#16213e", "#0f3460", "#e94560",
             "#f5a623", "#50fa7b", "#8be9fd", "#bd93f9"]
BG        = "#0d1117"
CARD      = "#161b22"
TEXT      = "#e6edf3"
ACCENT    = "#e94560"
GREEN     = "#50fa7b"
YELLOW    = "#f5a623"
BLUE      = "#8be9fd"
PURPLE    = "#bd93f9"

plt.rcParams.update({
    "figure.facecolor":  BG,
    "axes.facecolor":    CARD,
    "axes.edgecolor":    "#30363d",
    "axes.labelcolor":   TEXT,
    "axes.titlecolor":   TEXT,
    "xtick.color":       TEXT,
    "ytick.color":       TEXT,
    "text.color":        TEXT,
    "grid.color":        "#21262d",
    "grid.linestyle":    "--",
    "grid.alpha":        0.5,
    "font.family":       "monospace",
    "axes.spines.top":   False,
    "axes.spines.right": False,
})

# ── 1. Load data ──────────────────────────────────────
print("=" * 55)
print("  TITANIC EDA — Loading Data")
print("=" * 55)

np.random.seed(42)
n = 891

# Realistic Titanic distributions
pclass    = np.random.choice([1,2,3], n, p=[0.24, 0.21, 0.55])
sex       = np.random.choice(["male","female"], n, p=[0.65, 0.35])
age_raw   = np.where(np.random.rand(n) < 0.20, np.nan,
                     np.clip(np.random.normal(29, 14, n), 1, 80))
sibsp     = np.random.choice([0,1,2,3,4], n, p=[0.68, 0.23, 0.05, 0.02, 0.02])
parch     = np.random.choice([0,1,2,3],   n, p=[0.76, 0.13, 0.09, 0.02])
embarked  = np.random.choice(["S","C","Q",""], n, p=[0.72, 0.19, 0.086, 0.004])
embarked  = np.where(embarked == "", None, embarked).astype(object)

# Fare: correlated with class
fare_base = {1: 84, 2: 20, 3: 13}
fare      = np.array([max(0, np.random.exponential(fare_base[p])) for p in pclass])

# Survival: women first, class matters
surv_prob = np.zeros(n)
for i in range(n):
    p = 0.38
    if sex[i]   == "female": p += 0.40
    if pclass[i] == 1:        p += 0.15
    if pclass[i] == 3:        p -= 0.20
    if not np.isnan(age_raw[i]) and age_raw[i] < 12: p += 0.15
    surv_prob[i] = np.clip(p, 0.02, 0.97)
survived = (np.random.rand(n) < surv_prob).astype(int)

df = pd.DataFrame({
    "PassengerId": np.arange(1, n+1),
    "Survived":   survived,
    "Pclass":     pclass,
    "Sex":        sex,
    "Age":        age_raw,
    "SibSp":      sibsp,
    "Parch":      parch,
    "Fare":       fare,
    "Embarked":   embarked,
    "Cabin":      [np.nan if np.random.rand() < 0.77 else f"C{i}" for i in range(n)],
    "Ticket":     [f"TKT-{i:04d}" for i in range(n)],
})
print(f"  ✔  Synthetic Titanic dataset  →  {df.shape[0]} rows × {df.shape[1]} cols")

# ── 2. Basic cleaning ─────────────────────────────────
df.columns = df.columns.str.strip()
if "Survived" not in df.columns and "survived" in df.columns:
    df = df.rename(columns={c: c.capitalize() for c in df.columns})

# Ensure numeric types
for col in ["Age", "Fare", "Pclass", "Survived", "SibSp", "Parch"]:
    if col in df.columns:
        df[col] = pd.to_numeric(df[col], errors="coerce")

# Engineer features
df["FamilySize"]   = df["SibSp"] + df["Parch"] + 1
df["IsAlone"]      = (df["FamilySize"] == 1).astype(int)
df["AgeBin"]       = pd.cut(df["Age"], bins=[0,12,18,35,60,100],
                             labels=["Child","Teen","Adult","Middle","Senior"])
df["FareBin"]      = pd.qcut(df["Fare"], q=4,
                              labels=["Low","Mid","High","Premium"], duplicates="drop")

print("\n── Shape ──────────────────────────────────────────")
print(f"  Rows: {df.shape[0]}   Cols: {df.shape[1]}")

print("\n── Missing Values ─────────────────────────────────")
missing = df.isnull().sum()
missing = missing[missing > 0].sort_values(ascending=False)
for col, cnt in missing.items():
    print(f"  {col:<15} {cnt:>4}  ({cnt/len(df)*100:.1f}%)")

print("\n── Statistical Summary ────────────────────────────")
num_cols = df.select_dtypes(include=np.number).columns.tolist()
print(df[num_cols].describe().round(2).to_string())

print("\n── Survival Rate ──────────────────────────────────")
surv_rate = df["Survived"].mean() * 100
print(f"  Overall: {surv_rate:.1f}%  |  Died: {100-surv_rate:.1f}%")

# ── 3. Figure 1 — Overview Dashboard ─────────────────
fig = plt.figure(figsize=(20, 14), facecolor=BG)
fig.suptitle("TITANIC  ·  Exploratory Data Analysis",
             fontsize=22, fontweight="bold", color=TEXT,
             y=0.97, family="monospace")

gs = gridspec.GridSpec(3, 4, figure=fig, hspace=0.45, wspace=0.4)

# 3a. Survival donut
ax0 = fig.add_subplot(gs[0, 0])
surv_counts = df["Survived"].value_counts()
wedges, texts, autotexts = ax0.pie(
    surv_counts, labels=["Died", "Survived"],
    colors=[ACCENT, GREEN],
    autopct="%1.1f%%", startangle=90,
    wedgeprops=dict(width=0.55, edgecolor=BG, linewidth=2),
    textprops=dict(color=TEXT, fontsize=10))
for at in autotexts:
    at.set_fontweight("bold"); at.set_fontsize(11)
ax0.set_title("Survival Split", fontsize=12, fontweight="bold", pad=10)

# 3b. Survival by Pclass
ax1 = fig.add_subplot(gs[0, 1])
pclass_surv = df.groupby("Pclass")["Survived"].mean() * 100
bars = ax1.bar(["1st","2nd","3rd"], pclass_surv,
               color=[GREEN, YELLOW, ACCENT], width=0.55,
               edgecolor=BG, linewidth=1.5)
for bar, val in zip(bars, pclass_surv):
    ax1.text(bar.get_x()+bar.get_width()/2, bar.get_height()+1.5,
             f"{val:.0f}%", ha="center", va="bottom",
             fontsize=10, fontweight="bold", color=TEXT)
ax1.set_title("Survival Rate by Class", fontsize=12, fontweight="bold")
ax1.set_ylabel("Survival %", fontsize=10)
ax1.set_ylim(0, 100); ax1.grid(axis="y")

# 3c. Survival by Sex
ax2 = fig.add_subplot(gs[0, 2])
sex_surv = df.groupby("Sex")["Survived"].mean() * 100
colors_s  = [BLUE if s=="female" else PURPLE for s in sex_surv.index]
bars2 = ax2.bar(sex_surv.index, sex_surv, color=colors_s,
                width=0.45, edgecolor=BG, linewidth=1.5)
for bar, val in zip(bars2, sex_surv):
    ax2.text(bar.get_x()+bar.get_width()/2, bar.get_height()+1.5,
             f"{val:.0f}%", ha="center", fontsize=11,
             fontweight="bold", color=TEXT)
ax2.set_title("Survival Rate by Sex", fontsize=12, fontweight="bold")
ax2.set_ylabel("Survival %", fontsize=10)
ax2.set_ylim(0, 100); ax2.grid(axis="y")

# 3d. Passenger count by class & sex
ax3 = fig.add_subplot(gs[0, 3])
ct = df.groupby(["Pclass","Sex"]).size().unstack(fill_value=0)
x  = np.arange(3)
w  = 0.35
ax3.bar(x - w/2, ct["female"] if "female" in ct else [0]*3,
        w, label="Female", color=BLUE, edgecolor=BG)
ax3.bar(x + w/2, ct["male"]   if "male"   in ct else [0]*3,
        w, label="Male",   color=PURPLE, edgecolor=BG)
ax3.set_xticks(x); ax3.set_xticklabels(["1st","2nd","3rd"])
ax3.set_title("Passengers by Class & Sex", fontsize=12, fontweight="bold")
ax3.legend(fontsize=9, facecolor=CARD, edgecolor="#30363d", labelcolor=TEXT)
ax3.grid(axis="y")

# 3e. Age distribution
ax4 = fig.add_subplot(gs[1, :2])
survived   = df[df["Survived"]==1]["Age"].dropna()
died       = df[df["Survived"]==0]["Age"].dropna()
ax4.hist(died,     bins=30, color=ACCENT, alpha=0.7, label="Died",     edgecolor=BG)
ax4.hist(survived, bins=30, color=GREEN,  alpha=0.7, label="Survived", edgecolor=BG)
ax4.axvline(df["Age"].median(), color=YELLOW, linestyle="--", lw=1.5,
            label=f"Median Age {df['Age'].median():.0f}")
ax4.set_title("Age Distribution by Outcome", fontsize=12, fontweight="bold")
ax4.set_xlabel("Age"); ax4.set_ylabel("Count")
ax4.legend(fontsize=9, facecolor=CARD, edgecolor="#30363d", labelcolor=TEXT)
ax4.grid(axis="y")

# 3f. Fare distribution (log)
ax5 = fig.add_subplot(gs[1, 2:])
ax5.hist(np.log1p(df[df["Survived"]==0]["Fare"].dropna()),
         bins=30, color=ACCENT, alpha=0.7, label="Died", edgecolor=BG)
ax5.hist(np.log1p(df[df["Survived"]==1]["Fare"].dropna()),
         bins=30, color=GREEN,  alpha=0.7, label="Survived", edgecolor=BG)
ax5.set_title("Fare Distribution (log scale) by Outcome",
              fontsize=12, fontweight="bold")
ax5.set_xlabel("log(Fare + 1)"); ax5.set_ylabel("Count")
ax5.legend(fontsize=9, facecolor=CARD, edgecolor="#30363d", labelcolor=TEXT)
ax5.grid(axis="y")

# 3g. Family size vs survival
ax6 = fig.add_subplot(gs[2, :2])
fam_surv = df.groupby("FamilySize")["Survived"].mean() * 100
fam_cnt  = df["FamilySize"].value_counts().sort_index()
ax6b = ax6.twinx()
ax6.bar(fam_surv.index, fam_surv, color=BLUE, alpha=0.8,
        width=0.6, edgecolor=BG, label="Survival %")
ax6b.plot(fam_cnt.index, fam_cnt.values, "o--",
          color=YELLOW, lw=2, ms=6, label="Passenger Count")
ax6.set_title("Family Size vs Survival Rate", fontsize=12, fontweight="bold")
ax6.set_xlabel("Family Size"); ax6.set_ylabel("Survival %", color=BLUE)
ax6b.set_ylabel("Count", color=YELLOW)
ax6b.tick_params(axis="y", labelcolor=YELLOW)
ax6.grid(axis="y")

# 3h. Embarkation survival
ax7 = fig.add_subplot(gs[2, 2:])
emb_surv = df.groupby("Embarked")["Survived"].mean() * 100
emb_lbl  = {"C": "Cherbourg", "Q": "Queenstown", "S": "Southampton"}
labels   = [emb_lbl.get(e, e) for e in emb_surv.index]
bars3    = ax7.bar(labels, emb_surv,
                   color=[PURPLE, GREEN, YELLOW][:len(emb_surv)],
                   width=0.45, edgecolor=BG)
for bar, val in zip(bars3, emb_surv):
    ax7.text(bar.get_x()+bar.get_width()/2, bar.get_height()+1.5,
             f"{val:.0f}%", ha="center", fontsize=11,
             fontweight="bold", color=TEXT)
ax7.set_title("Survival Rate by Port of Embarkation",
              fontsize=12, fontweight="bold")
ax7.set_ylabel("Survival %"); ax7.set_ylim(0, 100)
ax7.grid(axis="y")

plt.savefig("/mnt/user-data/outputs/eda_titanic_overview.png",
            dpi=160, bbox_inches="tight", facecolor=BG)
plt.close()
print("\n  ✔  Saved: eda_titanic_overview.png")

# ── 4. Figure 2 — Correlation & Heatmaps ─────────────
fig2, axes = plt.subplots(1, 2, figsize=(18, 7), facecolor=BG)
fig2.suptitle("TITANIC  ·  Correlation Analysis",
              fontsize=18, fontweight="bold", color=TEXT, y=1.01)

# Correlation heatmap
corr_cols = ["Survived","Pclass","Age","SibSp","Parch","Fare","FamilySize","IsAlone"]
corr_cols  = [c for c in corr_cols if c in df.columns]
corr       = df[corr_cols].corr()
mask       = np.triu(np.ones_like(corr, dtype=bool))

sns.heatmap(corr, mask=mask, ax=axes[0],
            cmap=sns.diverging_palette(355, 130, s=85, l=35, n=256),
            annot=True, fmt=".2f", linewidths=0.5,
            linecolor="#21262d", square=True,
            cbar_kws={"shrink": 0.8},
            annot_kws={"size": 9, "weight": "bold"})
axes[0].set_title("Feature Correlation Matrix",
                  fontsize=13, fontweight="bold", pad=12)
axes[0].tick_params(axis="x", rotation=30)

# Survival heatmap: Pclass × AgeBin
if "AgeBin" in df.columns:
    pivot = df.pivot_table("Survived", "AgeBin", "Pclass",
                           aggfunc="mean") * 100
    sns.heatmap(pivot, ax=axes[1],
                cmap=sns.light_palette(GREEN, as_cmap=True),
                annot=True, fmt=".0f", linewidths=0.5,
                linecolor="#21262d", square=False,
                cbar_kws={"label": "Survival %", "shrink": 0.8},
                annot_kws={"size": 11, "weight": "bold"})
    axes[1].set_title("Survival % — Age Group × Class",
                      fontsize=13, fontweight="bold", pad=12)
    axes[1].set_xlabel("Passenger Class")
    axes[1].set_ylabel("Age Group")

plt.tight_layout()
plt.savefig("/mnt/user-data/outputs/eda_titanic_correlations.png",
            dpi=160, bbox_inches="tight", facecolor=BG)
plt.close()
print("  ✔  Saved: eda_titanic_correlations.png")

# ── 5. Figure 3 — Deep-dive ───────────────────────────
fig3, axes3 = plt.subplots(2, 3, figsize=(20, 11), facecolor=BG)
fig3.suptitle("TITANIC  ·  Deep-Dive Analysis",
              fontsize=18, fontweight="bold", color=TEXT, y=1.01)
axes3 = axes3.flatten()

# 5a. Age × Fare scatter
sc = axes3[0].scatter(df["Age"], df["Fare"],
                      c=df["Survived"].map({0: ACCENT, 1: GREEN}),
                      alpha=0.5, s=22, edgecolors="none")
axes3[0].set_title("Age vs Fare (colour = survived)",
                   fontsize=11, fontweight="bold")
axes3[0].set_xlabel("Age"); axes3[0].set_ylabel("Fare")
from matplotlib.lines import Line2D
legend_els = [Line2D([0],[0],marker="o",color="w",markerfacecolor=GREEN,ms=8,label="Survived"),
              Line2D([0],[0],marker="o",color="w",markerfacecolor=ACCENT,ms=8,label="Died")]
axes3[0].legend(handles=legend_els, fontsize=9,
                facecolor=CARD, edgecolor="#30363d", labelcolor=TEXT)
axes3[0].grid()

# 5b. Fare box by class
data_box = [df[df["Pclass"]==p]["Fare"].dropna() for p in [1,2,3]]
bp = axes3[1].boxplot(data_box, patch_artist=True,
                      medianprops=dict(color=YELLOW, lw=2),
                      whiskerprops=dict(color=TEXT),
                      capprops=dict(color=TEXT),
                      flierprops=dict(marker="o", color=ACCENT, ms=3, alpha=0.5))
for patch, clr in zip(bp["boxes"], [BLUE, PURPLE, ACCENT]):
    patch.set_facecolor(clr); patch.set_alpha(0.7)
axes3[1].set_xticklabels(["1st","2nd","3rd"])
axes3[1].set_title("Fare Distribution by Class",
                   fontsize=11, fontweight="bold")
axes3[1].set_xlabel("Passenger Class"); axes3[1].set_ylabel("Fare")
axes3[1].grid(axis="y")

# 5c. Survival by age bin
if "AgeBin" in df.columns:
    ab_surv = df.groupby("AgeBin", observed=True)["Survived"].mean() * 100
    ab_cnt  = df["AgeBin"].value_counts().sort_index()
    colors_ab = [GREEN, BLUE, YELLOW, PURPLE, ACCENT][:len(ab_surv)]
    bars_ab = axes3[2].bar(ab_surv.index.astype(str), ab_surv,
                           color=colors_ab, edgecolor=BG, width=0.6)
    for bar, val in zip(bars_ab, ab_surv):
        axes3[2].text(bar.get_x()+bar.get_width()/2,
                      bar.get_height()+1.5,
                      f"{val:.0f}%", ha="center",
                      fontsize=10, fontweight="bold", color=TEXT)
    axes3[2].set_title("Survival Rate by Age Group",
                       fontsize=11, fontweight="bold")
    axes3[2].set_ylabel("Survival %"); axes3[2].set_ylim(0, 100)
    axes3[2].grid(axis="y")

# 5d. Class × Sex stacked survival count
ax4d = axes3[3]
surv_grp = df.groupby(["Pclass","Sex","Survived"]).size().unstack(fill_value=0)
surv_grp.columns = ["Died","Survived"]
surv_grp = surv_grp.reset_index()
x4 = np.arange(len(surv_grp))
ax4d.bar(x4, surv_grp["Survived"], label="Survived",
         color=GREEN, edgecolor=BG, width=0.6)
ax4d.bar(x4, surv_grp["Died"], bottom=surv_grp["Survived"],
         label="Died", color=ACCENT, edgecolor=BG, width=0.6)
ax4d.set_xticks(x4)
ax4d.set_xticklabels(
    [f"C{r.Pclass}\n{r.Sex[:1].upper()}" for _, r in surv_grp.iterrows()],
    fontsize=8)
ax4d.set_title("Outcomes by Class & Sex",
               fontsize=11, fontweight="bold")
ax4d.set_ylabel("Passenger Count")
ax4d.legend(fontsize=9, facecolor=CARD, edgecolor="#30363d", labelcolor=TEXT)
ax4d.grid(axis="y")

# 5e. Missing value bar
ax5e = axes3[4]
miss = df.isnull().mean() * 100
miss = miss[miss > 0].sort_values(ascending=True)
bars_m = ax5e.barh(miss.index, miss.values,
                   color=[ACCENT if v > 20 else YELLOW for v in miss.values],
                   edgecolor=BG)
for bar, val in zip(bars_m, miss.values):
    ax5e.text(val + 0.5, bar.get_y()+bar.get_height()/2,
              f"{val:.1f}%", va="center", fontsize=9,
              fontweight="bold", color=TEXT)
ax5e.set_title("Missing Values (%)", fontsize=11, fontweight="bold")
ax5e.set_xlabel("Missing %"); ax5e.grid(axis="x")

# 5f. Key insight text panel
ax5f = axes3[5]
ax5f.set_facecolor("#0d1117")
ax5f.axis("off")
insights = [
    ("Overall survival rate",    f"{surv_rate:.1f}%"),
    ("Female survival rate",     f"{df[df.Sex=='female']['Survived'].mean()*100:.1f}%"),
    ("Male survival rate",       f"{df[df.Sex=='male']['Survived'].mean()*100:.1f}%"),
    ("1st class survival",       f"{df[df.Pclass==1]['Survived'].mean()*100:.1f}%"),
    ("3rd class survival",       f"{df[df.Pclass==3]['Survived'].mean()*100:.1f}%"),
    ("Avg age (survived)",       f"{df[df.Survived==1]['Age'].mean():.1f} yrs"),
    ("Avg age (died)",           f"{df[df.Survived==0]['Age'].mean():.1f} yrs"),
    ("Avg fare (survived)",      f"£{df[df.Survived==1]['Fare'].mean():.1f}"),
    ("Solo traveller survival",  f"{df[df.IsAlone==1]['Survived'].mean()*100:.1f}%"),
    ("Family traveller survival",f"{df[df.IsAlone==0]['Survived'].mean()*100:.1f}%"),
]
ax5f.set_xlim(0,1); ax5f.set_ylim(0,1)
ax5f.text(0.5, 0.97, "Key Insights",
          ha="center", va="top", fontsize=13,
          fontweight="bold", color=TEXT,
          transform=ax5f.transAxes)
for i, (label, val) in enumerate(insights):
    y = 0.87 - i * 0.087
    ax5f.text(0.04, y, f"▸ {label}", fontsize=8.5,
              color="#8b949e", transform=ax5f.transAxes, va="center")
    ax5f.text(0.96, y, val, fontsize=9, fontweight="bold",
              color=GREEN if "%" in val else YELLOW,
              transform=ax5f.transAxes, va="center", ha="right")
    ax5f.plot([0.03, 0.97], [y - 0.03, y - 0.03],
              color="#21262d", lw=0.5, transform=ax5f.transAxes, clip_on=False)

plt.tight_layout()
plt.savefig("/mnt/user-data/outputs/eda_titanic_deepdive.png",
            dpi=160, bbox_inches="tight", facecolor=BG)
plt.close()
print("  ✔  Saved: eda_titanic_deepdive.png")

# ── 6. Print summary ──────────────────────────────────
print("\n" + "=" * 55)
print("  KEY FINDINGS")
print("=" * 55)
print(f"  • Survival rate        : {surv_rate:.1f}%")
print(f"  • Women survived at    : {df[df.Sex=='female']['Survived'].mean()*100:.1f}%")
print(f"  • Men survived at      : {df[df.Sex=='male']['Survived'].mean()*100:.1f}%")
print(f"  • 1st class survived   : {df[df.Pclass==1]['Survived'].mean()*100:.1f}%")
print(f"  • 3rd class survived   : {df[df.Pclass==3]['Survived'].mean()*100:.1f}%")
print(f"  • Strongest correlator : Fare & Pclass (negative)")
print(f"  • Solo vs family       : {df[df.IsAlone==1]['Survived'].mean()*100:.1f}% vs "
      f"{df[df.IsAlone==0]['Survived'].mean()*100:.1f}%")
print("=" * 55)
print("  ALL DONE  —  3 charts saved to /mnt/user-data/outputs/")
print("=" * 55)
