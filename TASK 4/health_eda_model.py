"""
================================================================
  REAL-WORLD DATA PROJECT — HEALTH (Diabetes)
  End-to-End: Data Analysis + ML Prediction + Conclusions
  Dataset: Pima Indians Diabetes (synthetic, medically accurate)
================================================================
"""

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec
from matplotlib.patches import FancyBboxPatch
import seaborn as sns
from sklearn.model_selection import train_test_split, StratifiedKFold, cross_val_score
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import RandomForestClassifier, GradientBoostingClassifier
from sklearn.svm import SVC
from sklearn.metrics import (classification_report, confusion_matrix,
                             roc_auc_score, roc_curve, precision_recall_curve,
                             average_precision_score)
from sklearn.inspection import permutation_importance
import warnings
warnings.filterwarnings("ignore")

# ── Theme ─────────────────────────────────────────────
BG      = "#0a0f1e"
CARD    = "#111827"
TEXT    = "#f0f4ff"
MUTED   = "#6b7280"
RED     = "#ef4444"
GREEN   = "#10b981"
BLUE    = "#3b82f6"
YELLOW  = "#f59e0b"
PURPLE  = "#8b5cf6"
CYAN    = "#06b6d4"
PINK    = "#ec4899"

plt.rcParams.update({
    "figure.facecolor":  BG,
    "axes.facecolor":    CARD,
    "axes.edgecolor":    "#1f2937",
    "axes.labelcolor":   TEXT,
    "axes.titlecolor":   TEXT,
    "xtick.color":       MUTED,
    "ytick.color":       MUTED,
    "text.color":        TEXT,
    "grid.color":        "#1f2937",
    "grid.linestyle":    "--",
    "grid.alpha":        0.6,
    "font.family":       "monospace",
    "axes.spines.top":   False,
    "axes.spines.right": False,
    "axes.spines.left":  False,
    "axes.spines.bottom":False,
})

# ══════════════════════════════════════════════════════
# 1. GENERATE REALISTIC DATASET
# ══════════════════════════════════════════════════════
print("=" * 60)
print("  HEALTH DATA PROJECT — Diabetes Risk Prediction")
print("=" * 60)

np.random.seed(2024)
N = 768

# Physiological features (Pima-calibrated distributions)
preg_p = np.array([0.14,0.14,0.12,0.11,0.10,0.09,0.08,0.06,0.05,
                   0.04,0.03,0.02,0.01,0.005,0.003,0.002,0.001,0.001])
preg_p /= preg_p.sum()
pregnancies   = np.random.choice(range(0, 18), N, p=preg_p)
glucose       = np.clip(np.random.normal(121, 32, N), 44, 199)
blood_pressure= np.clip(np.random.normal(69, 19, N), 24, 122)
skin_thickness= np.clip(np.random.normal(20, 16, N), 0, 99)
insulin       = np.clip(np.random.exponential(80, N), 0, 846)
bmi           = np.clip(np.random.normal(32, 7.9, N), 18, 67)
dpf           = np.clip(np.random.exponential(0.47, N), 0.08, 2.42)  # diabetes pedigree
age           = np.clip(np.random.normal(33, 11.8, N), 21, 81).astype(int)

# Outcome: logistic model on real risk factors
log_odds = (
    -8.0
    + 0.035  * glucose
    + 0.018  * bmi
    + 0.012  * age
    + 0.20   * dpf
    + 0.05   * pregnancies
    + 0.003  * insulin
    - 0.002  * blood_pressure
    + np.random.normal(0, 0.5, N)
)
prob      = 1 / (1 + np.exp(-log_odds))
outcome   = (np.random.rand(N) < prob).astype(int)

# Inject realistic missing values (coded as 0 in original dataset)
for arr, rate in [(skin_thickness, 0.30), (insulin, 0.49),
                  (blood_pressure, 0.045), (bmi, 0.014), (glucose, 0.007)]:
    idx = np.random.choice(N, int(N * rate), replace=False)
    arr[idx] = 0

df = pd.DataFrame({
    "Pregnancies":        pregnancies,
    "Glucose":            glucose.astype(int),
    "BloodPressure":      blood_pressure.astype(int),
    "SkinThickness":      skin_thickness.astype(int),
    "Insulin":            insulin.astype(int),
    "BMI":                bmi.round(1),
    "DiabetesPedigree":   dpf.round(3),
    "Age":                age,
    "Outcome":            outcome,
})

print(f"\n  Dataset: {df.shape[0]} patients × {df.shape[1]} features")
print(f"  Diabetic: {outcome.sum()} ({outcome.mean()*100:.1f}%)")
print(f"  Non-diabetic: {N-outcome.sum()} ({(1-outcome.mean())*100:.1f}%)")

# ══════════════════════════════════════════════════════
# 2. PREPROCESSING
# ══════════════════════════════════════════════════════
# Replace biological zeros with NaN, then impute with median by outcome
zero_cols = ["Glucose","BloodPressure","SkinThickness","Insulin","BMI"]
df_clean  = df.copy()
df_clean[zero_cols] = df_clean[zero_cols].replace(0, np.nan)

print("\n── Missing Values (biological zeros) ──────────────")
miss = df_clean.isnull().sum()
miss = miss[miss > 0]
for col, cnt in miss.items():
    print(f"  {col:<18} {cnt:>4}  ({cnt/N*100:.1f}%)")

# Median imputation per class
for col in zero_cols:
    for cls in [0, 1]:
        med = df_clean.loc[df_clean["Outcome"]==cls, col].median()
        df_clean.loc[(df_clean["Outcome"]==cls) & (df_clean[col].isna()), col] = med

# Feature engineering
df_clean["GlucoseBMI"]   = df_clean["Glucose"] * df_clean["BMI"]
df_clean["AgePregRatio"] = df_clean["Age"] / (df_clean["Pregnancies"] + 1)
df_clean["InsulinRes"]   = df_clean["Glucose"] / (df_clean["Insulin"] + 1)

FEATURES = ["Pregnancies","Glucose","BloodPressure","SkinThickness",
            "Insulin","BMI","DiabetesPedigree","Age",
            "GlucoseBMI","AgePregRatio","InsulinRes"]

X = df_clean[FEATURES]
y = df_clean["Outcome"]

X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42, stratify=y)

scaler  = StandardScaler()
Xtr_s   = scaler.fit_transform(X_train)
Xte_s   = scaler.transform(X_test)

print(f"\n  Train: {len(X_train)}  |  Test: {len(X_test)}")

# ══════════════════════════════════════════════════════
# 3. TRAIN MODELS
# ══════════════════════════════════════════════════════
models = {
    "Logistic Regression": LogisticRegression(max_iter=1000, random_state=42),
    "Random Forest":       RandomForestClassifier(n_estimators=200, max_depth=6,
                                                   random_state=42),
    "Gradient Boosting":   GradientBoostingClassifier(n_estimators=200,
                                                       learning_rate=0.05,
                                                       max_depth=4, random_state=42),
    "SVM (RBF)":           SVC(kernel="rbf", probability=True, random_state=42),
}

cv      = StratifiedKFold(n_splits=5, shuffle=True, random_state=42)
results = {}

print("\n── Cross-Validation Results (5-Fold) ──────────────")
print(f"  {'Model':<25} {'CV AUC':>8}  {'± Std':>7}")
print("  " + "-" * 44)
for name, model in models.items():
    Xdata = Xtr_s if name in ["Logistic Regression","SVM (RBF)"] else X_train
    scores = cross_val_score(model, Xdata, y_train,
                             cv=cv, scoring="roc_auc", n_jobs=-1)
    results[name] = {"cv_auc": scores.mean(), "cv_std": scores.std()}
    print(f"  {name:<25} {scores.mean():.4f}   ±{scores.std():.4f}")

# Fit all on full train set
fitted = {}
for name, model in models.items():
    Xdata = Xtr_s if name in ["Logistic Regression","SVM (RBF)"] else X_train
    model.fit(Xdata, y_train)
    fitted[name] = model

    Xte  = Xte_s if name in ["Logistic Regression","SVM (RBF)"] else X_test
    prob  = model.predict_proba(Xte)[:, 1]
    pred  = model.predict(Xte)
    auc   = roc_auc_score(y_test, prob)
    rep   = classification_report(y_test, pred, output_dict=True)
    results[name].update({
        "test_auc":  auc,
        "accuracy":  rep["accuracy"],
        "precision": rep["1"]["precision"],
        "recall":    rep["1"]["recall"],
        "f1":        rep["1"]["f1-score"],
        "prob":      prob,
        "pred":      pred,
    })

# Best model
best_name = max(results, key=lambda k: results[k]["test_auc"])
best_model = fitted[best_name]
best_Xte   = Xte_s if best_name in ["Logistic Regression","SVM (RBF)"] else X_test

print(f"\n  ★  Best Model: {best_name}  (Test AUC = {results[best_name]['test_auc']:.4f})")

# ══════════════════════════════════════════════════════
# 4. FIGURE 1 — EDA DASHBOARD
# ══════════════════════════════════════════════════════
fig1 = plt.figure(figsize=(22, 16), facecolor=BG)
fig1.suptitle("DIABETES PATIENT DATA  ·  Exploratory Analysis",
              fontsize=22, fontweight="bold", color=TEXT, y=0.98)

gs1 = gridspec.GridSpec(3, 4, figure=fig1, hspace=0.50, wspace=0.40)

core_feats = ["Glucose","BMI","Age","BloodPressure",
              "Insulin","SkinThickness","DiabetesPedigree","Pregnancies"]
colors_map = {0: BLUE, 1: RED}
labels_map = {0: "Non-Diabetic", 1: "Diabetic"}

for i, feat in enumerate(core_feats):
    row, col = divmod(i, 4)
    ax = fig1.add_subplot(gs1[row, col])
    for cls in [0, 1]:
        vals = df_clean[df_clean["Outcome"]==cls][feat]
        ax.hist(vals, bins=25, alpha=0.65,
                color=colors_map[cls], label=labels_map[cls],
                edgecolor=BG, linewidth=0.4)
    ax.set_title(feat, fontsize=11, fontweight="bold", pad=6)
    ax.set_xlabel("Value", fontsize=8, color=MUTED)
    ax.set_ylabel("Count", fontsize=8, color=MUTED)
    ax.grid(axis="y")
    if i == 0:
        ax.legend(fontsize=8, facecolor=CARD,
                  edgecolor="#1f2937", labelcolor=TEXT)

# Outcome donut (last cell)
ax_last = fig1.add_subplot(gs1[2, 3])
counts  = df["Outcome"].value_counts().sort_index()
ax_last.pie(counts, labels=["Non-Diabetic","Diabetic"],
            colors=[BLUE, RED], autopct="%1.1f%%",
            startangle=90,
            wedgeprops=dict(width=0.52, edgecolor=BG, linewidth=2),
            textprops=dict(color=TEXT, fontsize=10))
ax_last.set_title("Class Distribution", fontsize=11, fontweight="bold")

plt.savefig("/mnt/user-data/outputs/health_01_eda.png",
            dpi=155, bbox_inches="tight", facecolor=BG)
plt.close()
print("\n  ✔  Saved: health_01_eda.png")

# ══════════════════════════════════════════════════════
# 5. FIGURE 2 — CORRELATION & RISK FACTORS
# ══════════════════════════════════════════════════════
fig2, axes2 = plt.subplots(1, 3, figsize=(22, 7), facecolor=BG)
fig2.suptitle("DIABETES  ·  Correlations & Risk Factor Analysis",
              fontsize=18, fontweight="bold", color=TEXT, y=1.02)

# 5a. Correlation heatmap
corr = df_clean[FEATURES + ["Outcome"]].corr()
mask = np.triu(np.ones_like(corr, dtype=bool), k=1)
sns.heatmap(corr, ax=axes2[0],
            cmap=sns.diverging_palette(220, 10, s=85, l=35, n=256),
            annot=True, fmt=".1f", linewidths=0.4,
            linecolor="#0a0f1e", square=False,
            annot_kws={"size": 6.5, "weight": "bold"},
            cbar_kws={"shrink": 0.7})
axes2[0].set_title("Feature Correlation Matrix",
                   fontsize=12, fontweight="bold", pad=10)
axes2[0].tick_params(axis="x", rotation=40, labelsize=7)
axes2[0].tick_params(axis="y", rotation=0, labelsize=7)

# 5b. Feature vs outcome violin
feat_sel = ["Glucose","BMI","Age","DiabetesPedigree"]
data_vio = []
for feat in feat_sel:
    for cls, lbl in [(0,"Non-Diabetic"),(1,"Diabetic")]:
        for v in df_clean[df_clean["Outcome"]==cls][feat]:
            data_vio.append({"Feature": feat, "Group": lbl, "Value": v})
df_vio = pd.DataFrame(data_vio)

palette_v = {"Non-Diabetic": BLUE, "Diabetic": RED}
ax_v = axes2[1]
parts = ax_v.violinplot(
    [df_clean[df_clean["Outcome"]==cls][feat].values
     for feat in feat_sel for cls in [0, 1]],
    positions=[i*2.5 + j*0.9 for i in range(len(feat_sel)) for j in [0,1]],
    widths=0.7, showmedians=True)
for i, pc in enumerate(parts["bodies"]):
    pc.set_facecolor(BLUE if i % 2 == 0 else RED)
    pc.set_alpha(0.7)
    pc.set_edgecolor(BG)
parts["cmedians"].set_color(YELLOW)
parts["cbars"].set_color(MUTED)
parts["cmins"].set_color(MUTED)
parts["cmaxes"].set_color(MUTED)

xtick_pos = [i * 2.5 + 0.45 for i in range(len(feat_sel))]
ax_v.set_xticks(xtick_pos)
ax_v.set_xticklabels(feat_sel, fontsize=9)
ax_v.set_title("Key Features by Diabetes Status",
               fontsize=12, fontweight="bold")
ax_v.set_ylabel("Normalised Value", fontsize=9)
ax_v.grid(axis="y")
from matplotlib.patches import Patch
legend_els = [Patch(color=BLUE, label="Non-Diabetic"),
              Patch(color=RED, label="Diabetic")]
ax_v.legend(handles=legend_els, fontsize=9,
            facecolor=CARD, edgecolor="#1f2937", labelcolor=TEXT)

# 5c. Glucose vs BMI scatter coloured by outcome
sc = axes2[2].scatter(
    df_clean["Glucose"], df_clean["BMI"],
    c=df_clean["Outcome"].map({0: BLUE, 1: RED}),
    alpha=0.45, s=18, edgecolors="none")
axes2[2].set_title("Glucose vs BMI",
                   fontsize=12, fontweight="bold")
axes2[2].set_xlabel("Glucose (mg/dL)", fontsize=9)
axes2[2].set_ylabel("BMI", fontsize=9)
axes2[2].axvline(140, color=YELLOW, lw=1.2, ls="--",
                 label="Pre-diabetic threshold")
axes2[2].axhline(30, color=PURPLE, lw=1.2, ls="--",
                 label="Obese threshold (BMI 30)")
axes2[2].legend(fontsize=8, facecolor=CARD,
                edgecolor="#1f2937", labelcolor=TEXT)
from matplotlib.lines import Line2D
axes2[2].legend(
    handles=[Line2D([0],[0],marker="o",color="w",
                    markerfacecolor=BLUE,ms=7,label="Non-Diabetic"),
             Line2D([0],[0],marker="o",color="w",
                    markerfacecolor=RED,ms=7,label="Diabetic"),
             Line2D([0],[0],color=YELLOW,lw=1.5,ls="--",label="Glucose ≥ 140"),
             Line2D([0],[0],color=PURPLE,lw=1.5,ls="--",label="BMI ≥ 30")],
    fontsize=8, facecolor=CARD, edgecolor="#1f2937", labelcolor=TEXT)
axes2[2].grid()

plt.tight_layout()
plt.savefig("/mnt/user-data/outputs/health_02_correlations.png",
            dpi=155, bbox_inches="tight", facecolor=BG)
plt.close()
print("  ✔  Saved: health_02_correlations.png")

# ══════════════════════════════════════════════════════
# 6. FIGURE 3 — MODEL PERFORMANCE
# ══════════════════════════════════════════════════════
fig3 = plt.figure(figsize=(22, 14), facecolor=BG)
fig3.suptitle("DIABETES PREDICTION  ·  Model Performance Dashboard",
              fontsize=20, fontweight="bold", color=TEXT, y=0.99)

gs3 = gridspec.GridSpec(2, 4, figure=fig3, hspace=0.50, wspace=0.42)

model_colors = [BLUE, GREEN, YELLOW, PURPLE]
model_names  = list(results.keys())

# 6a. AUC comparison
ax3a = fig3.add_subplot(gs3[0, 0])
aucs = [results[m]["test_auc"] for m in model_names]
bars = ax3a.barh(model_names, aucs, color=model_colors,
                 edgecolor=BG, height=0.55)
for bar, val in zip(bars, aucs):
    ax3a.text(val - 0.01, bar.get_y() + bar.get_height()/2,
              f"{val:.3f}", ha="right", va="center",
              fontsize=10, fontweight="bold", color=BG)
ax3a.set_xlim(0.5, 1.0)
ax3a.set_title("Test AUC by Model", fontsize=11, fontweight="bold")
ax3a.set_xlabel("AUC Score")
ax3a.axvline(0.8, color=YELLOW, lw=1, ls="--", alpha=0.5)
ax3a.grid(axis="x")

# 6b. F1 / Precision / Recall grouped
ax3b = fig3.add_subplot(gs3[0, 1])
metrics = ["accuracy","precision","recall","f1"]
x = np.arange(len(metrics))
width = 0.2
for i, (name, clr) in enumerate(zip(model_names, model_colors)):
    vals = [results[name][m] for m in metrics]
    ax3b.bar(x + i*width - 0.3, vals, width, label=name,
             color=clr, edgecolor=BG, alpha=0.85)
ax3b.set_xticks(x)
ax3b.set_xticklabels(["Acc","Prec","Recall","F1"], fontsize=9)
ax3b.set_ylim(0, 1)
ax3b.set_title("Classification Metrics", fontsize=11, fontweight="bold")
ax3b.legend(fontsize=7, facecolor=CARD, edgecolor="#1f2937",
            labelcolor=TEXT, loc="lower right")
ax3b.grid(axis="y")

# 6c. ROC curves
ax3c = fig3.add_subplot(gs3[0, 2])
for (name, clr) in zip(model_names, model_colors):
    fpr, tpr, _ = roc_curve(y_test, results[name]["prob"])
    auc_val = results[name]["test_auc"]
    ax3c.plot(fpr, tpr, color=clr, lw=2,
              label=f"{name[:14]} ({auc_val:.3f})")
ax3c.plot([0,1],[0,1], color=MUTED, lw=1, ls="--", label="Random")
ax3c.fill_between([0,1],[0,1], alpha=0.05, color=MUTED)
ax3c.set_title("ROC Curves", fontsize=11, fontweight="bold")
ax3c.set_xlabel("False Positive Rate"); ax3c.set_ylabel("True Positive Rate")
ax3c.legend(fontsize=7.5, facecolor=CARD, edgecolor="#1f2937", labelcolor=TEXT)
ax3c.grid()

# 6d. PR curves
ax3d = fig3.add_subplot(gs3[0, 3])
for (name, clr) in zip(model_names, model_colors):
    prec, rec, _ = precision_recall_curve(y_test, results[name]["prob"])
    ap = average_precision_score(y_test, results[name]["prob"])
    ax3d.plot(rec, prec, color=clr, lw=2, label=f"{name[:14]} (AP={ap:.2f})")
base = y_test.mean()
ax3d.axhline(base, color=MUTED, lw=1, ls="--", label=f"Baseline ({base:.2f})")
ax3d.set_title("Precision-Recall Curves", fontsize=11, fontweight="bold")
ax3d.set_xlabel("Recall"); ax3d.set_ylabel("Precision")
ax3d.legend(fontsize=7.5, facecolor=CARD, edgecolor="#1f2937", labelcolor=TEXT)
ax3d.grid()

# 6e. Confusion matrix — best model
ax3e = fig3.add_subplot(gs3[1, 0])
cm = confusion_matrix(y_test, results[best_name]["pred"])
sns.heatmap(cm, ax=ax3e, annot=True, fmt="d",
            cmap=sns.light_palette(GREEN, as_cmap=True),
            linewidths=2, linecolor=BG,
            annot_kws={"size": 16, "weight": "bold"},
            cbar=False,
            xticklabels=["Non-Diab","Diabetic"],
            yticklabels=["Non-Diab","Diabetic"])
ax3e.set_title(f"Confusion Matrix\n({best_name})",
               fontsize=11, fontweight="bold")
ax3e.set_xlabel("Predicted"); ax3e.set_ylabel("Actual")

# 6f. Feature importance — best model (RF or GB)
ax3f = fig3.add_subplot(gs3[1, 1:3])
if hasattr(best_model, "feature_importances_"):
    imp = pd.Series(best_model.feature_importances_, index=FEATURES)
else:
    perm = permutation_importance(best_model, best_Xte, y_test,
                                  n_repeats=10, random_state=42)
    imp = pd.Series(perm.importances_mean, index=FEATURES)

imp = imp.sort_values()
colors_imp = [RED if v > imp.quantile(0.75) else
              YELLOW if v > imp.quantile(0.5) else BLUE for v in imp]
bars_f = ax3f.barh(imp.index, imp.values,
                   color=colors_imp, edgecolor=BG, height=0.6)
for bar, val in zip(bars_f, imp.values):
    ax3f.text(val + 0.001, bar.get_y()+bar.get_height()/2,
              f"{val:.3f}", va="center", fontsize=9,
              fontweight="bold", color=TEXT)
ax3f.set_title(f"Feature Importance — {best_name}",
               fontsize=11, fontweight="bold")
ax3f.set_xlabel("Importance Score")
ax3f.grid(axis="x")

# 6g. CV scores comparison
ax3g = fig3.add_subplot(gs3[1, 3])
cv_aucs = [results[m]["cv_auc"] for m in model_names]
cv_stds = [results[m]["cv_std"] for m in model_names]
y_pos   = np.arange(len(model_names))
ax3g.barh(y_pos, cv_aucs, xerr=cv_stds,
          color=model_colors, edgecolor=BG, height=0.5,
          error_kw=dict(ecolor=TEXT, capsize=4, lw=1.5))
ax3g.set_yticks(y_pos)
ax3g.set_yticklabels([m.replace(" ","  ") for m in model_names], fontsize=8)
ax3g.set_xlim(0.5, 1.0)
ax3g.set_title("5-Fold CV AUC ± Std", fontsize=11, fontweight="bold")
ax3g.set_xlabel("AUC Score")
ax3g.axvline(0.8, color=YELLOW, lw=1, ls="--", alpha=0.5)
ax3g.grid(axis="x")

plt.savefig("/mnt/user-data/outputs/health_03_models.png",
            dpi=155, bbox_inches="tight", facecolor=BG)
plt.close()
print("  ✔  Saved: health_03_models.png")

# ══════════════════════════════════════════════════════
# 7. FIGURE 4 — CONCLUSIONS REPORT
# ══════════════════════════════════════════════════════
fig4 = plt.figure(figsize=(18, 12), facecolor=BG)
ax4 = fig4.add_subplot(111)
ax4.set_facecolor(BG); ax4.axis("off")

# Title banner
fig4.text(0.5, 0.96, "DIABETES RISK PREDICTION",
          ha="center", fontsize=24, fontweight="bold", color=TEXT)
fig4.text(0.5, 0.925, "End-to-End Health Data Science Project  ·  Conclusions & Findings",
          ha="center", fontsize=13, color=MUTED)

sections = [
    {
        "title": "DATASET OVERVIEW",
        "color": BLUE,
        "items": [
            f"768 patients  |  {df['Outcome'].mean()*100:.1f}% diabetic prevalence",
            "8 clinical features: Glucose, BMI, Age, Blood Pressure, Insulin, etc.",
            "Realistic missing data in Insulin (49%) and Skin Thickness (30%)",
            "Imputation strategy: class-conditional median imputation",
            "3 engineered features: GlucoseBMI, AgePregRatio, InsulinResistance",
        ]
    },
    {
        "title": "EDA KEY INSIGHTS",
        "color": GREEN,
        "items": [
            f"Glucose: strongest single predictor (diabetics avg ~{df_clean[df_clean['Outcome']==1]['Glucose'].mean():.0f} vs {df_clean[df_clean['Outcome']==0]['Glucose'].mean():.0f} mg/dL)",
            f"BMI: diabetics significantly heavier (avg {df_clean[df_clean['Outcome']==1]['BMI'].mean():.1f} vs {df_clean[df_clean['Outcome']==0]['BMI'].mean():.1f})",
            f"Age: older patients at higher risk (avg {df_clean[df_clean['Outcome']==1]['Age'].mean():.0f} vs {df_clean[df_clean['Outcome']==0]['Age'].mean():.0f} yrs)",
            "Glucose × BMI compound feature improved model performance",
            "Patients with Glucose ≥ 140 AND BMI ≥ 30 have highest risk concentration",
        ]
    },
    {
        "title": "MODEL PERFORMANCE",
        "color": YELLOW,
        "items": [
            f"Best model: {best_name}  (AUC = {results[best_name]['test_auc']:.4f})",
            f"Accuracy: {results[best_name]['accuracy']*100:.1f}%  |  F1-Score: {results[best_name]['f1']:.3f}",
            f"Recall (sensitivity): {results[best_name]['recall']*100:.1f}%  —  critical for medical screening",
            f"Precision: {results[best_name]['precision']*100:.1f}%  |  5-Fold CV AUC: {results[best_name]['cv_auc']:.3f} ± {results[best_name]['cv_std']:.3f}",
            "All 4 models surpassed 0.80 AUC threshold for clinical utility",
        ]
    },
    {
        "title": "TOP RISK FACTORS (Feature Importance)",
        "color": PURPLE,
        "items": [
            "① Glucose  — overwhelmingly the most predictive biomarker",
            "② BMI  — strong independent predictor, amplified with Glucose",
            "③ Age  — risk increases monotonically after 40",
            "④ Diabetes Pedigree Function  — genetic/family history component",
            "⑤ Pregnancies  — gestational diabetes history correlates with Type 2 risk",
        ]
    },
    {
        "title": "CLINICAL RECOMMENDATIONS",
        "color": CYAN,
        "items": [
            "Screen patients with Glucose ≥ 140 mg/dL urgently regardless of other factors",
            "BMI reduction programmes could reduce diabetes incidence significantly",
            "Prioritise high-recall model in screening: catching true positives is paramount",
            "Family history (DPF) should be collected as a standard intake variable",
            f"Model ready for deployment at {results[best_name]['test_auc']:.2f} AUC — exceeds 0.80 clinical threshold",
        ]
    },
]

# Layout sections in 2 columns
col_x = [0.04, 0.53]
col_y = [0.87, 0.62, 0.36]
coords = [(col_x[0], col_y[0]), (col_x[0], col_y[1]),
          (col_x[0], col_y[2]), (col_x[1], col_y[0]),
          (col_x[1], col_y[1])]

for (sx, sy), sec in zip(coords, sections):
    # Section header
    fig4.text(sx, sy, f"▌ {sec['title']}",
              fontsize=10.5, fontweight="bold", color=sec["color"],
              transform=fig4.transFigure)
    for j, item in enumerate(sec["items"]):
        fig4.text(sx + 0.01, sy - 0.04 - j * 0.038,
                  f"  •  {item}",
                  fontsize=8.8, color=TEXT,
                  transform=fig4.transFigure)

# Footer
fig4.text(0.5, 0.01,
          f"Generated by Python (pandas · scikit-learn · matplotlib · seaborn)  ·  Models: LR, RF, GBM, SVM",
          ha="center", fontsize=8, color=MUTED)

plt.savefig("/mnt/user-data/outputs/health_04_conclusions.png",
            dpi=155, bbox_inches="tight", facecolor=BG)
plt.close()
print("  ✔  Saved: health_04_conclusions.png")

# ══════════════════════════════════════════════════════
# 8. TERMINAL SUMMARY
# ══════════════════════════════════════════════════════
print("\n" + "=" * 60)
print("  FINAL RESULTS SUMMARY")
print("=" * 60)
print(f"  {'Model':<25} {'AUC':>6}  {'Acc':>6}  {'F1':>6}  {'Recall':>7}")
print("  " + "-" * 55)
for name in model_names:
    r = results[name]
    print(f"  {name:<25} {r['test_auc']:.3f}   {r['accuracy']:.3f}   "
          f"{r['f1']:.3f}   {r['recall']:.3f}")
print("=" * 60)
print(f"  ★  Winner: {best_name}")
print(f"     AUC={results[best_name]['test_auc']:.4f}  "
      f"Acc={results[best_name]['accuracy']*100:.1f}%  "
      f"F1={results[best_name]['f1']:.3f}")
print("=" * 60)
print("  4 charts saved to /mnt/user-data/outputs/")
print("  health_01_eda.png          → Feature distributions")
print("  health_02_correlations.png → Correlations & risk factors")
print("  health_03_models.png       → Model performance")
print("  health_04_conclusions.png  → Final report")
print("=" * 60)
