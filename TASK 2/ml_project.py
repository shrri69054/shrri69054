"""
Predictive Modeling Using Machine Learning
==========================================
Dataset   : Synthetic Student Placement Dataset
Problem   : Binary Classification — Placed / Not Placed
Algorithms: Logistic Regression, Decision Tree, Random Forest
Author    : Shrrivathsan | Panimalar Engineering College
Due       : 20 May 2026
"""

import numpy as np
import pandas as pd
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec
import seaborn as sns
import warnings
warnings.filterwarnings('ignore')

from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.tree import DecisionTreeClassifier, export_text
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import (
    accuracy_score, precision_score, recall_score, f1_score,
    confusion_matrix, roc_curve, auc, classification_report
)

np.random.seed(42)

# ═══════════════════════════════════════════════
# 1. GENERATE DATASET
# ═══════════════════════════════════════════════
n = 800
cgpa       = np.round(np.random.uniform(5.0, 10.0, n), 2)
iq         = np.random.randint(85, 145, n)
internship = np.random.choice([0, 1], n, p=[0.4, 0.6])
projects   = np.random.randint(0, 6, n)
backlogs   = np.random.randint(0, 5, n)
comm_skill = np.random.randint(1, 6, n)   # 1-5 scale
dept       = np.random.choice(['CSE','ECE','MECH','CIVIL','IT'], n)

# Placement logic (realistic weights)
log_odds = (
    0.9  * (cgpa - 7.0)
    + 0.04 * (iq - 110)
    + 1.2  * internship
    + 0.4  * projects
    - 0.6  * backlogs
    + 0.5  * (comm_skill - 3)
    + np.random.normal(0, 0.8, n)
)
prob   = 1 / (1 + np.exp(-log_odds))
placed = (prob > 0.5).astype(int)

df = pd.DataFrame({
    'cgpa': cgpa, 'iq': iq, 'internship': internship,
    'projects': projects, 'backlogs': backlogs,
    'comm_skill': comm_skill, 'department': dept,
    'placed': placed
})

print("=" * 58)
print("  PREDICTIVE MODELING — STUDENT PLACEMENT")
print("=" * 58)
print(f"\nDataset shape : {df.shape}")
print(f"Placement rate: {placed.mean()*100:.1f}%  ({placed.sum()} placed / {(1-placed).sum()} not placed)")

# ═══════════════════════════════════════════════
# 2. PREPROCESSING
# ═══════════════════════════════════════════════
le = LabelEncoder()
df['dept_enc'] = le.fit_transform(df['department'])

features = ['cgpa','iq','internship','projects','backlogs','comm_skill','dept_enc']
X = df[features]
y = df['placed']

X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42, stratify=y)

scaler = StandardScaler()
X_train_sc = scaler.fit_transform(X_train)
X_test_sc  = scaler.transform(X_test)

print(f"\nTrain size : {len(X_train)}   Test size : {len(X_test)}")

# ═══════════════════════════════════════════════
# 3. TRAIN MODELS
# ═══════════════════════════════════════════════
models = {
    'Logistic Regression': LogisticRegression(max_iter=500, random_state=42),
    'Decision Tree'      : DecisionTreeClassifier(max_depth=6, random_state=42),
    'Random Forest'      : RandomForestClassifier(n_estimators=150, max_depth=8, random_state=42),
}

results = {}
for name, model in models.items():
    Xtr = X_train_sc if name == 'Logistic Regression' else X_train
    Xte = X_test_sc  if name == 'Logistic Regression' else X_test
    model.fit(Xtr, y_train)
    y_pred = model.predict(Xte)
    y_prob = model.predict_proba(Xte)[:, 1]
    cv     = cross_val_score(model,
                             X_train_sc if name == 'Logistic Regression' else X_train,
                             y_train, cv=5, scoring='accuracy')
    results[name] = {
        'model': model, 'y_pred': y_pred, 'y_prob': y_prob,
        'accuracy' : accuracy_score(y_test, y_pred),
        'precision': precision_score(y_test, y_pred),
        'recall'   : recall_score(y_test, y_pred),
        'f1'       : f1_score(y_test, y_pred),
        'cv_mean'  : cv.mean(), 'cv_std': cv.std(),
        'cm'       : confusion_matrix(y_test, y_pred),
    }
    print(f"\n── {name} ──")
    print(f"   Accuracy : {results[name]['accuracy']:.4f}")
    print(f"   Precision: {results[name]['precision']:.4f}")
    print(f"   Recall   : {results[name]['recall']:.4f}")
    print(f"   F1-Score : {results[name]['f1']:.4f}")
    print(f"   CV-Acc   : {results[name]['cv_mean']:.4f} ± {results[name]['cv_std']:.4f}")

best_name = max(results, key=lambda k: results[k]['f1'])
print(f"\n★ Best Model: {best_name}  (F1 = {results[best_name]['f1']:.4f})")

# ═══════════════════════════════════════════════
# 4. VISUALIZATION DASHBOARD
# ═══════════════════════════════════════════════
BG   = '#07070f'
CARD = '#0f0f1e'
ACC  = '#e94560'
GRN  = '#2dc653'
PRP  = '#7c5cbf'
BLU  = '#4fc3f7'
ORG  = '#f5a623'
WHT  = '#d0d0e8'
DIM  = '#505070'

MODEL_COLORS = {'Logistic Regression': BLU, 'Decision Tree': ORG, 'Random Forest': GRN}

fig = plt.figure(figsize=(22, 26), facecolor=BG)
fig.suptitle('ML Dashboard — Student Placement Prediction',
             fontsize=24, fontweight='bold', color='white', y=0.99)

gs = gridspec.GridSpec(4, 3, figure=fig, hspace=0.45, wspace=0.35,
                       left=0.06, right=0.97, top=0.96, bottom=0.03)

def sax(ax, title):
    ax.set_facecolor(CARD)
    ax.set_title(title, color='white', fontsize=12, fontweight='bold', pad=10)
    ax.tick_params(colors=WHT, labelsize=9)
    ax.xaxis.label.set_color(WHT)
    ax.yaxis.label.set_color(WHT)
    for sp in ax.spines.values():
        sp.set_edgecolor('#1e1e38')

# ── Row 0: Metric bars for each model
metric_names = ['accuracy','precision','recall','f1']
metric_labels = ['Accuracy','Precision','Recall','F1-Score']

ax_metrics = fig.add_subplot(gs[0, :])
x      = np.arange(len(metric_names))
width  = 0.22
for i, (mname, res) in enumerate(results.items()):
    vals = [res[m] for m in metric_names]
    bars = ax_metrics.bar(x + i*width, vals, width,
                          label=mname, color=MODEL_COLORS[mname],
                          edgecolor=BG, linewidth=1.2, alpha=0.92)
    for bar, v in zip(bars, vals):
        ax_metrics.text(bar.get_x() + bar.get_width()/2,
                        bar.get_height() + 0.005,
                        f'{v:.3f}', ha='center', va='bottom',
                        color='white', fontsize=8, fontweight='bold')
ax_metrics.set_xticks(x + width)
ax_metrics.set_xticklabels(metric_labels, fontsize=11)
ax_metrics.set_ylim(0, 1.12)
ax_metrics.set_ylabel('Score')
ax_metrics.legend(facecolor=CARD, labelcolor='white', fontsize=10, loc='upper right')
sax(ax_metrics, 'Model Performance Comparison — All Metrics')
ax_metrics.axhline(0.9, color=ACC, linewidth=0.8, linestyle='--', alpha=0.5)
ax_metrics.text(2.85, 0.902, '0.90 threshold', color=ACC, fontsize=8)

# ── Row 1: Confusion Matrices (3 subplots)
for col, (mname, res) in enumerate(results.items()):
    ax = fig.add_subplot(gs[1, col])
    cm = res['cm']
    sns.heatmap(cm, annot=True, fmt='d', cmap='RdYlGn', ax=ax,
                linewidths=2, linecolor=BG,
                annot_kws={'size': 14, 'weight': 'bold', 'color': 'black'},
                cbar=False,
                xticklabels=['Not Placed','Placed'],
                yticklabels=['Not Placed','Placed'])
    ax.set_facecolor(CARD)
    ax.set_title(f'Confusion Matrix\n{mname}', color='white', fontsize=11, fontweight='bold', pad=8)
    ax.tick_params(colors=WHT, labelsize=9)
    ax.set_xlabel('Predicted', color=WHT)
    ax.set_ylabel('Actual', color=WHT)

# ── Row 2: ROC Curves (col 0-1) + CV Bar (col 2)
ax_roc = fig.add_subplot(gs[2, :2])
for mname, res in results.items():
    fpr, tpr, _ = roc_curve(y_test, res['y_prob'])
    roc_auc     = auc(fpr, tpr)
    ax_roc.plot(fpr, tpr, color=MODEL_COLORS[mname], linewidth=2.5,
                label=f"{mname}  (AUC = {roc_auc:.3f})")
ax_roc.plot([0,1],[0,1], color=DIM, linewidth=1, linestyle='--')
ax_roc.fill_between([0,1],[0,1], alpha=0.05, color=DIM)
sax(ax_roc, 'ROC Curves — All Models')
ax_roc.set_xlabel('False Positive Rate')
ax_roc.set_ylabel('True Positive Rate')
ax_roc.legend(facecolor=CARD, labelcolor='white', fontsize=10)
ax_roc.set_xlim(0, 1); ax_roc.set_ylim(0, 1.05)

ax_cv = fig.add_subplot(gs[2, 2])
cv_means = [res['cv_mean'] for res in results.values()]
cv_stds  = [res['cv_std']  for res in results.values()]
bars_cv  = ax_cv.bar(list(results.keys()), cv_means,
                     color=list(MODEL_COLORS.values()),
                     yerr=cv_stds, capsize=6,
                     error_kw={'color':'white','linewidth':1.5},
                     edgecolor=BG, linewidth=1.2, alpha=0.9)
ax_cv.set_ylim(0, 1.1)
for bar, v in zip(bars_cv, cv_means):
    ax_cv.text(bar.get_x() + bar.get_width()/2, bar.get_height() + 0.03,
               f'{v:.3f}', ha='center', color='white', fontsize=9, fontweight='bold')
sax(ax_cv, '5-Fold CV Accuracy ± Std')
ax_cv.set_ylabel('CV Accuracy')
ax_cv.set_xticklabels(['LR', 'DT', 'RF'], fontsize=10)

# ── Row 3: Feature Importance (RF) + CGPA Distribution + Summary
rf_model   = results['Random Forest']['model']
importances = rf_model.feature_importances_
feat_df    = pd.DataFrame({'feature': features, 'importance': importances})
feat_df    = feat_df.sort_values('importance', ascending=True)

ax_feat = fig.add_subplot(gs[3, 0])
colors_f = [GRN if v == feat_df['importance'].max() else BLU for v in feat_df['importance']]
ax_feat.barh(feat_df['feature'], feat_df['importance'],
             color=colors_f, edgecolor=BG, linewidth=1)
for i, (v, name) in enumerate(zip(feat_df['importance'], feat_df['feature'])):
    ax_feat.text(v + 0.002, i, f'{v:.3f}', va='center', color='white', fontsize=8)
sax(ax_feat, 'Feature Importance (Random Forest)')
ax_feat.set_xlabel('Importance')

ax_dist = fig.add_subplot(gs[3, 1])
placed_cgpa   = df[df['placed']==1]['cgpa']
unplaced_cgpa = df[df['placed']==0]['cgpa']
ax_dist.hist(placed_cgpa, bins=25, color=GRN, alpha=0.7, label='Placed', edgecolor=BG)
ax_dist.hist(unplaced_cgpa, bins=25, color=ACC, alpha=0.7, label='Not Placed', edgecolor=BG)
ax_dist.axvline(placed_cgpa.mean(), color=GRN, linewidth=1.5, linestyle='--')
ax_dist.axvline(unplaced_cgpa.mean(), color=ACC, linewidth=1.5, linestyle='--')
sax(ax_dist, 'CGPA Distribution by Placement')
ax_dist.set_xlabel('CGPA')
ax_dist.set_ylabel('Count')
ax_dist.legend(facecolor=CARD, labelcolor='white', fontsize=9)

ax_sum = fig.add_subplot(gs[3, 2])
ax_sum.set_facecolor(CARD)
ax_sum.axis('off')
ax_sum.set_title('Model Summary', color='white', fontsize=12, fontweight='bold', pad=10)
best_res = results[best_name]
summary_lines = [
    ('Dataset',         '800 records, 7 features'),
    ('Target',          'Placed / Not Placed'),
    ('Train / Test',    '640 / 160 (80/20)'),
    ('Best Model',      best_name),
    ('Best Accuracy',   f"{best_res['accuracy']*100:.2f}%"),
    ('Best F1-Score',   f"{best_res['f1']:.4f}"),
    ('Best Precision',  f"{best_res['precision']:.4f}"),
    ('Best Recall',     f"{best_res['recall']:.4f}"),
]
for i, (k, v) in enumerate(summary_lines):
    y = 0.88 - i * 0.115
    ax_sum.text(0.03, y, k, transform=ax_sum.transAxes,
                color='#8888bb', fontsize=10)
    ax_sum.text(0.97, y, v, transform=ax_sum.transAxes,
                color=GRN if i >= 3 else WHT,
                fontsize=10, fontweight='bold', ha='right')
    ax_sum.axhline(y - 0.04, xmin=0.02, xmax=0.98,
                   color='#1e1e38', linewidth=0.6)

plt.savefig('/mnt/user-data/outputs/ml_dashboard.png', dpi=150,
            bbox_inches='tight', facecolor=BG)
plt.close()
print("\n[DONE] ml_dashboard.png saved")

# ── Classification Report (best model)
Xte_best = X_test_sc if best_name == 'Logistic Regression' else X_test
print(f"\n── Classification Report: {best_name} ──")
print(classification_report(y_test, results[best_name]['y_pred'],
                             target_names=['Not Placed','Placed']))
