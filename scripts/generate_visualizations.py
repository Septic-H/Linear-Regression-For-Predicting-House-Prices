import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

# load data
data = pd.read_csv('../data/real_estate_data.csv')

# create the output directory if it doesn't exist
os.makedirs('../visuals', exist_ok=True)

# Visualizations
# Correlation heatmap
plt.figure(figsize=(10, 6))
sns.heatmap(data.iloc[:, 1:].corr(), annot=True, cmap='coolwarm', fmt=".2f")
plt.savefig('../visuals/correlation_heatmap.png')

# Scatter plots for each feature against house price
features = ['X1 transaction date', 'X2 house age', 'X3 distance to the nearest MRT station', 'X4 number of convenience stores', 'X5 latitude', 'X6 longitude']
target = 'Y house price of unit area'

features_file_names = ['X1', 'X2', 'X3', 'X4', 'X5', 'X6']
target_file_name = 'Y'
n = 0

for feature in features:
    plt.figure(figsize=(10, 6))
    sns.scatterplot(x=feature, y=target, data=data)
    plt.title(f'{target} vs {feature}')
    plt.savefig(f'../visuals/{target_file_name}_vs_{features_file_names[n]}.png')
    n += 1

print("Visualizations saved in 'visuals/' directory.")
