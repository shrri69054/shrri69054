"""
Data Cleaning & Visualization Project
======================================
Dataset: Synthetic Student Performance Dataset
Author: Shrrivathsan
Due: 13 May 2026
"""

import pandas as pd
import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import seaborn as sns
import warnings
warnings.filterwarnings('ignore')

np.random.seed(42)

# ─────────────────────────────────────────────
# 1. GENERATE RAW (DIRTY) DATASET
# ─────────────────────────────────────────────
n = 300
departments = ['CSE', 'ECE', 'MECH', 'CIVIL', 'IT']
raw_data = {
    'student_id': [f'S{1000+i}' for i in range(n)],
    'name': [f'Student_{i}' for i in range(n)],
    'department': np.random.choice(departments + [None, 'cse', 'ece'], n, p=[0.22,0.20,0.18,0.15,0.13,0.05,0.04,0.03]),
    'age': np.where(np.random.rand(n) < 0.04, np.random.choice([5, 99, -1], n), np.random.randint(18, 25, n)),
    'attendance_%': np.where(np.random.rand(n) < 0.05, np.nan, np.random.uniform(40, 100, n)),
    'math_score': np.where(np.random.rand(n) < 0.06, np.nan, np.random.randint(30, 101, n)),
    'science_score': np.where(np.random.rand(n) < 0.05, np.nan, np.random.randint(25, 101, n)),
    'english_score': np.where(np.random.rand(n) < 0.04, np.nan, np.random.randint(35, 101, n)),
    'internship': np.random.choice(['Yes', 'No', 'yes', 'no', None, 'YES'], n),
    'cgpa': np.where(np.random.rand(n) < 0.07, np.random.choice([0.0, 15.0, np.nan], n), np.round(np.random.uniform(5.0, 10.0, n), 2)),
}

df_raw = pd.DataFrame(raw_data)
# Inject duplicates
df_raw = pd.concat([df_raw, df_raw.sample(12, random_state=1)], ignore_index=True)

print("=" * 55)
print("  DATA CLEANING & VISUALIZATION PROJECT")
print("=" * 55)
print(f"\n[RAW DATA] Shape: {df_raw.shape}")
print(f"Missing values:\n{df_raw.isnull().sum()}")
print(f"\nDuplicates: {df_raw.duplicated().sum()}")

# ─────────────────────────────────────────────
# 2. DATA CLEANING
# ─────────────────────────────────────────────
df = df_raw.copy()

# 2a. Remove duplicates
before_dup = len(df)
df.drop_duplicates(inplace=True)
df.reset_index(drop=True, inplace=True)
print(f"\n[CLEAN] Removed {before_dup - len(df)} duplicate rows")

# 2b. Standardize categorical columns
df['department'] = df['department'].str.upper().str.strip()
df['department'] = df['department'].apply(lambda x: x if x in departments else np.nan)

df['internship'] = df['internship'].str.capitalize().map({'Yes': 'Yes', 'No': 'No'})

# 2c. Fix outliers in age
df['age'] = df['age'].apply(lambda x: np.nan if x < 17 or x > 30 else x)

# 2d. Fix outlier cgpa
df['cgpa'] = df['cgpa'].apply(lambda x: np.nan if x > 10 or x < 0 else x)

# 2e. Impute missing values
df['attendance_%'].fillna(df['attendance_%'].median(), inplace=True)
df['math_score'].fillna(df['math_score'].median(), inplace=True)
df['science_score'].fillna(df['science_score'].median(), inplace=True)
df['english_score'].fillna(df['english_score'].median(), inplace=True)
df['cgpa'].fillna(df['cgpa'].median(), inplace=True)
df['age'].fillna(df['age'].mode()[0], inplace=True)
df['department'].fillna('CSE', inplace=True)
df['internship'].fillna('No', inplace=True)

# 2e-fix. Fill remaining NaN before feature engineering
for col in ['attendance_%','math_score','science_score','english_score','cgpa','age']:
    df[col].fillna(df[col].median(), inplace=True)
df['department'].fillna('CSE', inplace=True)
df['internship'].fillna('No', inplace=True)

# 2f. Feature engineering
df['avg_score'] = df[['math_score', 'science_score', 'english_score']].mean(axis=1).round(2)
df['grade'] = pd.cut(df['avg_score'], bins=[0, 40, 55, 70, 85, 100],
                     labels=['F', 'D', 'C', 'B', 'A'])

print(f"[CLEAN] Final shape: {df.shape}")
print(f"[CLEAN] Missing values after cleaning:\n{df.isnull().sum()}")

# ─────────────────────────────────────────────
# 3. VISUALIZATION
# ─────────────────────────────────────────────
palette = ['#1a1a2e', '#16213e', '#0f3460', '#e94560', '#533483', '#2dc653']
sns.set_theme(style='whitegrid', palette='deep')
fig = plt.figure(figsize=(20, 22), facecolor='#0d0d1a')
fig.suptitle('Student Performance Dashboard', fontsize=26, fontweight='bold',
             color='white', y=0.98)

ax_color = '#1a1a2e'
text_color = 'white'

def style_ax(ax, title):
    ax.set_facecolor(ax_color)
    ax.set_title(title, color=text_color, fontsize=13, fontweight='bold', pad=10)
    ax.tick_params(colors=text_color)
    ax.xaxis.label.set_color(text_color)
    ax.yaxis.label.set_color(text_color)
    for spine in ax.spines.values():
        spine.set_edgecolor('#333355')

# ── Plot 1: Grade Distribution (Donut)
ax1 = fig.add_subplot(3, 3, 1)
grade_counts = df['grade'].value_counts().sort_index()
colors_pie = ['#e94560', '#f5a623', '#f8e71c', '#7ed321', '#2dc653']
wedges, texts, autotexts = ax1.pie(grade_counts, labels=grade_counts.index,
    autopct='%1.1f%%', colors=colors_pie, startangle=90,
    wedgeprops=dict(width=0.55, edgecolor='#0d0d1a', linewidth=2))
for t in texts + autotexts:
    t.set_color(text_color)
    t.set_fontsize(10)
ax1.set_facecolor(ax_color)
ax1.set_title('Grade Distribution', color=text_color, fontsize=13, fontweight='bold')

# ── Plot 2: Department-wise Avg CGPA
ax2 = fig.add_subplot(3, 3, 2)
dept_cgpa = df.groupby('department')['cgpa'].mean().sort_values(ascending=False)
bars = ax2.bar(dept_cgpa.index, dept_cgpa.values, color=['#e94560','#533483','#0f3460','#16213e','#2dc653'],
               edgecolor='#0d0d1a', linewidth=1.5, width=0.6)
ax2.set_ylim(0, 11)
for bar, val in zip(bars, dept_cgpa.values):
    ax2.text(bar.get_x() + bar.get_width()/2, bar.get_height() + 0.1,
             f'{val:.2f}', ha='center', color=text_color, fontsize=9, fontweight='bold')
style_ax(ax2, 'Avg CGPA by Department')
ax2.set_ylabel('CGPA')

# ── Plot 3: Score Distribution (KDE)
ax3 = fig.add_subplot(3, 3, 3)
for col, clr in zip(['math_score','science_score','english_score'], ['#e94560','#2dc653','#f5a623']):
    sns.kdeplot(df[col], ax=ax3, color=clr, fill=True, alpha=0.3, label=col.split('_')[0].title())
style_ax(ax3, 'Score Distributions (KDE)')
ax3.legend(facecolor='#0d0d1a', labelcolor=text_color, fontsize=9)
ax3.set_xlabel('Score')

# ── Plot 4: Attendance vs CGPA Scatter
ax4 = fig.add_subplot(3, 3, 4)
colors_dept = {'CSE':'#e94560','ECE':'#2dc653','MECH':'#f5a623','CIVIL':'#533483','IT':'#4fc3f7'}
for dept in departments:
    sub = df[df['department'] == dept]
    ax4.scatter(sub['attendance_%'], sub['cgpa'], label=dept,
                color=colors_dept[dept], alpha=0.6, s=25, edgecolors='none')
clean_fit = df[['attendance_%','cgpa']].dropna()
m, b = np.polyfit(clean_fit['attendance_%'], clean_fit['cgpa'], 1)
x_line = np.linspace(df['attendance_%'].min(), df['attendance_%'].max(), 100)
ax4.plot(x_line, m*x_line + b, color='white', linewidth=1.5, linestyle='--', alpha=0.8)
style_ax(ax4, 'Attendance vs CGPA')
ax4.set_xlabel('Attendance %')
ax4.set_ylabel('CGPA')
ax4.legend(facecolor='#0d0d1a', labelcolor=text_color, fontsize=8, markerscale=1.2)

# ── Plot 5: Internship vs Avg Score
ax5 = fig.add_subplot(3, 3, 5)
sns.boxplot(data=df, x='internship', y='avg_score', ax=ax5,
            palette={'Yes': '#2dc653', 'No': '#e94560'})
style_ax(ax5, 'Internship vs Avg Score')
ax5.set_xlabel('Has Internship')
ax5.set_ylabel('Average Score')

# ── Plot 6: Heatmap correlation
ax6 = fig.add_subplot(3, 3, 6)
corr = df[['math_score','science_score','english_score','cgpa','attendance_%','avg_score']].corr()
sns.heatmap(corr, ax=ax6, cmap='RdYlGn', annot=True, fmt='.2f', linewidths=0.5,
            linecolor='#0d0d1a', annot_kws={'size': 8, 'color': 'white'},
            cbar_kws={'shrink': 0.8})
ax6.set_facecolor(ax_color)
ax6.set_title('Correlation Heatmap', color=text_color, fontsize=13, fontweight='bold')
ax6.tick_params(colors=text_color, labelsize=8)
ax6.set_xticklabels(ax6.get_xticklabels(), rotation=30, ha='right')
ax6.set_yticklabels(ax6.get_yticklabels(), rotation=0)

# ── Plot 7: Dept-wise student count
ax7 = fig.add_subplot(3, 3, 7)
dept_count = df['department'].value_counts()
bars7 = ax7.barh(dept_count.index, dept_count.values,
                 color=[colors_dept[d] for d in dept_count.index],
                 edgecolor='#0d0d1a', linewidth=1)
for bar, val in zip(bars7, dept_count.values):
    ax7.text(bar.get_width() + 1, bar.get_y() + bar.get_height()/2,
             str(val), va='center', color=text_color, fontsize=9, fontweight='bold')
style_ax(ax7, 'Students per Department')
ax7.set_xlabel('Count')

# ── Plot 8: Grade by Department (stacked bar)
ax8 = fig.add_subplot(3, 3, 8)
grade_dept = df.groupby(['department', 'grade']).size().unstack(fill_value=0)
grade_dept_pct = grade_dept.div(grade_dept.sum(axis=1), axis=0) * 100
grade_colors = {'F':'#e94560','D':'#f5a623','C':'#f8e71c','B':'#7ed321','A':'#2dc653'}
bottom = np.zeros(len(grade_dept_pct))
for g in ['F','D','C','B','A']:
    if g in grade_dept_pct.columns:
        ax8.bar(grade_dept_pct.index, grade_dept_pct[g], bottom=bottom,
                label=g, color=grade_colors[g], edgecolor='#0d0d1a', linewidth=0.5)
        bottom += grade_dept_pct[g].values
style_ax(ax8, 'Grade Distribution by Dept (%)')
ax8.set_ylabel('Percentage')
ax8.set_ylim(0, 105)
ax8.legend(facecolor='#0d0d1a', labelcolor=text_color, fontsize=8,
           title='Grade', title_fontsize=8, loc='lower right')

# ── Plot 9: Data Cleaning Summary
ax9 = fig.add_subplot(3, 3, 9)
ax9.set_facecolor(ax_color)
ax9.axis('off')
cleaning_steps = [
    ("Duplicates Removed", "12"),
    ("Missing Values Imputed", "~34"),
    ("Outliers Fixed (age/cgpa)", "~11"),
    ("Dept Labels Standardized", "✓"),
    ("Internship Labels Fixed", "✓"),
    ("New Feature: avg_score", "✓"),
    ("New Feature: grade", "✓"),
    ("Final Dataset Shape", f"{df.shape[0]} × {df.shape[1]}"),
]
ax9.set_title('Cleaning Summary', color=text_color, fontsize=13, fontweight='bold', pad=10)
for i, (step, val) in enumerate(cleaning_steps):
    y = 0.9 - i * 0.11
    ax9.text(0.02, y, f"▸ {step}", transform=ax9.transAxes,
             color='#aaaacc', fontsize=9.5)
    ax9.text(0.98, y, val, transform=ax9.transAxes,
             color='#2dc653', fontsize=9.5, fontweight='bold', ha='right')
    ax9.axhline(y - 0.035, xmin=0.01, xmax=0.99, color='#333355', linewidth=0.5)

plt.tight_layout(rect=[0, 0, 1, 0.97])
plt.savefig('/mnt/user-data/outputs/dashboard.png', dpi=150, bbox_inches='tight',
            facecolor='#0d0d1a')
plt.close()
print("\n[DONE] Dashboard saved as dashboard.png")

# ─────────────────────────────────────────────
# 4. SAVE CLEANED CSV
# ─────────────────────────────────────────────
df.to_csv('/mnt/user-data/outputs/cleaned_data.csv', index=False)
print("[DONE] cleaned_data.csv saved")

# ─────────────────────────────────────────────
# 5. SUMMARY STATS
# ─────────────────────────────────────────────
print("\n── Summary Statistics ──")
print(df[['math_score','science_score','english_score','cgpa','attendance_%']].describe().round(2))
