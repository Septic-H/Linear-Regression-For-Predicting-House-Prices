import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

# Load data
data = pd.read_csv('C:/Users/Huzaifa/Desktop/Docs - BSE/Semester 3/CSC241 - Object Oriented Programming/Linear-Regression-For-Predicting-House-Prices/data/real_estate_data.csv')

# Create the output directory if it doesn't exist
os.makedirs('visuals', exist_ok=True)

# Visualizations
# Pairplot of all features
sns.pairplot(data)
plt.savefig('C:/Users/Huzaifa/Desktop/Docs - BSE/Semester 3/CSC241 - Object Oriented Programming/Linear-Regression-For-Predicting-House-Prices/visuals/pairplot.png')

# Correlation heatmap
plt.figure(figsize=(10, 8))
sns.heatmap(data.corr(), annot=True, cmap='coolwarm', linewidths=0.5)
plt.savefig('C:/Users/Huzaifa/Desktop/Docs - BSE/Semester 3/CSC241 - Object Oriented Programming/Linear-Regression-For-Predicting-House-Prices/visuals/correlation_heatmap.png')

# Scatter plots for each feature against house price
features = ['X1 transaction date', 'X2 house age', 'X3 distance to the nearest MRT station', 'X4 number of convenience stores', 'X5 latitude', 'X6 longitude']
target = 'Y house price of unit area'

for feature in features:
    plt.figure(figsize=(10, 6))
    sns.scatterplot(x=feature, y=target, data=data)
    plt.title(f'{target} vs {feature}')
    plt.savefig(f'C:/Users/Huzaifa/Desktop/Docs - BSE/Semester 3/CSC241 - Object Oriented Programming/Linear-Regression-For-Predicting-House-Prices/visuals/{target}_vs_{feature}.png')

print("Visualizations saved in 'visuals/' directory.")
