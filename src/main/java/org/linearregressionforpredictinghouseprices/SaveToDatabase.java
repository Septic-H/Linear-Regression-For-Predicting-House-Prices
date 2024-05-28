package org.linearregressionforpredictinghouseprices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveToDatabase {
    private static final String INSERT_QUERY = "INSERT INTO predictedprices (houseage, distancetomrt, numconveniencestores, latitude, longitude, predictedprice) VALUES (?, ?, ?, ?, ?, ?)";

    public void saveToDatabase(double houseAge, double distanceToMRT, int numConvenienceStores, double latitude, double longitude, double predictedPrice) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = System.getenv("DB_PASSWORD");

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

            preparedStatement.setDouble(1, houseAge);
            preparedStatement.setDouble(2, distanceToMRT);
            preparedStatement.setInt(3, numConvenienceStores);
            preparedStatement.setDouble(4, latitude);
            preparedStatement.setDouble(5, longitude);
            preparedStatement.setDouble(6, predictedPrice);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}

