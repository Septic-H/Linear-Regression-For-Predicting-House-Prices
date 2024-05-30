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

    private static double[] calculateCoefficients(double[][] X, double[] y) {
        int numFeatures = X[0].length;
        int numSamples = y.length;

        // compute X^T * X
        double[][] XtX = new double[numFeatures][numFeatures];
        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < numFeatures; j++) {
                for (int k = 0; k < numSamples; k++) {
                    XtX[i][j] += X[k][i] * X[k][j];
                }
            }
        }

        // compute X^T * y
        double[] Xty = new double[numFeatures];
        for (int i = 0; i < numFeatures; i++) {
            for (int k = 0; k < numSamples; k++) {
                Xty[i] += X[k][i] * y[k];
            }
        }

        // compute the inverse of X^T * X
        double[][] XtX_inv = invertMatrix(XtX);

        // compute the coefficients: (X^T * X)^-1 * X^T * y
        double[] coefficients = new double[numFeatures];
        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < numFeatures; j++) {
                coefficients[i] += XtX_inv[i][j] * Xty[j];
            }
        }

        return coefficients;
    }

    // matrix inversion using Gauss-Jordan elimination
    private static double[][] invertMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] augmentedMatrix = new double[n][2*n];

        // create augmented matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = matrix[i][j];
            }
            augmentedMatrix[i][i+n] = 1;
        }

        // apply Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            // Make the diagonal contain all 1's
            double diagonalElement = augmentedMatrix[i][i];
            for (int j = 0; j < 2*n; j++) {
                augmentedMatrix[i][j] /= diagonalElement;
            }

            // make the other elements in the current column 0
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix[k][i];
                    for (int j = 0; j < 2*n; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }
        }

        // extract the inverse matrix
        double[][] inverseMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseMatrix[i][j] = augmentedMatrix[i][j+n];
            }
        }

        return inverseMatrix;
    }

    public double predictPrice(double houseAge, double distanceToMRT, int numConvenienceStores, double latitude, double longitude) {
        double price = coefficients[0] * houseAge + coefficients[1] * distanceToMRT + coefficients[2] * numConvenienceStores + coefficients[3] * latitude + coefficients[4] * longitude;

        // Y= house price of unit area (10000 New Taiwan Dollar/Ping, where Ping is a local unit, 1 Ping = 3.3 meter squared)
        // this figure shows the price of a house in taiwan new dollar per 1 ping(3.3 meter squared)
        price *= 10000;

        price = Math.round(price * 100.0) / 100.0;

        return price;
    }
}
