import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

# load data
data = pd.read_csv('../data/real_estate_data.csv')
predicted_prices = pd.read_csv('../data/predicted_prices_through_model.csv')

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

# Line Plot comparing actual prices vs predicted prices
plt.figure(figsize=(10, 6))
sns.lineplot(data=data['Y house price of unit area'], label='Actual Price')
sns.lineplot(data=predicted_prices['Y house price of unit area'], label='Predicted Price')
plt.title('Actual Price vs Predicted Price - Line Plot')
plt.savefig('../visuals/actual_vs_predicted_price_lineplot.png')

# Bar plot comparing actual prices vs predicted prices
plt.figure(figsize=(50, 10))
barWidth = 0.3
bars1 = data['Y house price of unit area']
bars2 = predicted_prices['Y house price of unit area']
r1 = range(len(bars1))
r2 = [x + barWidth for x in r1]
plt.bar(r1, bars1, width=barWidth, label='Actual Price')
plt.bar(r2, bars2, width=barWidth, label='Predicted Price')
plt.xlim(min(min(r1), min(r2)) - barWidth / 2, max(max(r1), max(r2)) + barWidth / 2)
plt.xlabel('Serial No.')
plt.ylabel('Price per Unit Area')
plt.legend()
plt.title('Actual Price vs Predicted Price - Bar Plot')
plt.savefig('../visuals/actual_vs_predicted_price_barplot.png')