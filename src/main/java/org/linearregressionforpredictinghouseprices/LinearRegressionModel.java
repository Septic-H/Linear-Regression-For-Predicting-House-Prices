package org.linearregressionforpredictinghouseprices;

import java.util.List;

public class LinearRegressionModel {
    private double[] coefficients;

    public void trainModel(List<RealEstateRecord> realEstateRecords) {
        int numRecords = realEstateRecords.size();
        int numFeatures = 5;
        double[][] X = new double[numRecords][numFeatures];
        double[] y = new double[numRecords];

        for (int i = 0; i < numRecords; i++) {
            RealEstateRecord record = realEstateRecords.get(i);
            X[i][0] = record.getHouseAge();
            X[i][1] = record.getDistanceToMRT();
            X[i][2] = record.getNumConvenienceStores();
            X[i][3] = record.getLatitude();
            X[i][4] = record.getLongitude();
            y[i] = record.getPrice();
        }

        coefficients = calculateCoefficients(X, y);
    }

    private double[] calculateCoefficients(double[][] X, double[] y) {
        int numFeatures = X[0].length;
        double[] coefficients = new double[numFeatures];
        double[] residuals = new double[y.length];

        for (int i = 0; i < numFeatures; i++) {
            double sum = 0;
            for (int j = 0; j < y.length; j++) {
                sum += X[j][i] * y[j];
            }
            double mean = sum / y.length;
            coefficients[i] = mean;
        }

        for (int i = 0; i < y.length; i++) {
            double prediction = 0;
            for (int j = 0; j < numFeatures; j++) {
                prediction += coefficients[j] * X[i][j];
            }
            residuals[i] = y[i] - prediction;
        }

        return coefficients;
    }

    public double predictPrice(double houseAge, double distanceToMRT, int numConvenienceStores, double latitude, double longitude) {
        double price = coefficients[0] * houseAge + coefficients[1] * distanceToMRT + coefficients[2] * numConvenienceStores + coefficients[3] * latitude + coefficients[4] * longitude;

        // price is predicted in taiwan new dollar, conversion to usd
        price = price * 0.031075628;

        price = Math.round(price * 100.0) / 100.0;

        return price;
    }
}
