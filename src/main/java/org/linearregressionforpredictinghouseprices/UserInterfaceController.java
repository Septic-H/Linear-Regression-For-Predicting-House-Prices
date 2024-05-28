package org.linearregressionforpredictinghouseprices;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.List;

public class UserInterfaceController {
    @FXML
    private TextField houseAgeField;

    @FXML
    private TextField distanceToMRTField;

    @FXML
    private TextField numConvenienceStoresField;

    @FXML
    private TextField latitudeField;

    @FXML
    private TextField longitudeField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField predictedPriceField;

    @FXML
    private Button predictPriceButton;

    private final RealEstateData realEstateData = new RealEstateData();
    private final LinearRegressionModel regressionModel = new LinearRegressionModel();

    @FXML
    private void initialize() {
        predictPriceButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                houseAgeField.getText().isEmpty() || distanceToMRTField.getText().isEmpty() || numConvenienceStoresField.getText().isEmpty() || latitudeField.getText().isEmpty() || longitudeField.getText().isEmpty(),
                        houseAgeField.textProperty(),
                        distanceToMRTField.textProperty(),
                        numConvenienceStoresField.textProperty(),
                        latitudeField.textProperty(),
                        longitudeField.textProperty()
                )
        );
    }

    @FXML
    private void handleInputValidation(javafx.scene.input.KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        String text = textField.getText();

        if (textField == latitudeField || textField == longitudeField) {
            if (!text.matches("-?[0-9.]*")) {
                textField.setText(text.replaceAll("[^-0-9.]", ""));
            }
        } else {
            if (!text.matches("[0-9.]*")) {
                textField.setText(text.replaceAll("[^0-9.]", ""));
            }
        }

        validateInputs();
    }


    private boolean validateInputs() {
        String errorMessage = "";

        try {
            double houseAge = Double.parseDouble(houseAgeField.getText());
            if (houseAge <= 0) {
                errorMessage = "House age must be greater than zero.";
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errorLabel.setText(errorMessage.isEmpty() ? "House age must be a valid number." : errorMessage);
            errorLabel.setStyle("-fx-font-style: italic; -fx-text-fill: darkred;");
            GridPane.setRowIndex(errorLabel, 1);
            return false;
        }

        try {
            double distanceToMRT = Double.parseDouble(distanceToMRTField.getText());
            if (distanceToMRT <= 0) {
                errorMessage = "Distance to MRT must be greater than zero.";
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errorLabel.setText(errorMessage.isEmpty() ? "Distance to MRT must be a valid number." : errorMessage);
            errorLabel.setStyle("-fx-font-style: italic; -fx-text-fill: darkred;");
            GridPane.setRowIndex(errorLabel, 2);
            return false;
        }

        try {
            int numConvenienceStores = Integer.parseInt(numConvenienceStoresField.getText());
            if (numConvenienceStores < 0) {
                errorMessage = "Number of convenience stores must not be less than zero.";
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errorLabel.setText(errorMessage.isEmpty() ? "Number of convenience stores must be a valid integer." : errorMessage);
            errorLabel.setStyle("-fx-font-style: italic; -fx-text-fill: darkred;");
            GridPane.setRowIndex(errorLabel, 3);
            return false;
        }

        try {
            double latitude = Double.parseDouble(latitudeField.getText());
            if (latitude < -90 || latitude > 90) {
                errorMessage = "Latitude must be between -90 and 90.";
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errorLabel.setText(errorMessage.isEmpty() ? "Latitude must be a valid number." : errorMessage);
            errorLabel.setStyle("-fx-font-style: italic; -fx-text-fill: darkred;");
            GridPane.setRowIndex(errorLabel, 4);
            return false;
        }

        try {
            double longitude = Double.parseDouble(longitudeField.getText());
            if (longitude < -180 || longitude > 180) {
                errorMessage = "Longitude must be between -180 and 180.";
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errorLabel.setText(errorMessage.isEmpty() ? "Longitude must be a valid number." : errorMessage);
            errorLabel.setStyle("-fx-font-style: italic; -fx-text-fill: darkred;");
            GridPane.setRowIndex(errorLabel, 5);
            return false;
        }

        errorLabel.setText("Input is valid");
        errorLabel.setStyle("-fx-font-style: italic; -fx-text-fill: green;");
        GridPane.setRowIndex(errorLabel, 6);
        return true;
    }

    @FXML
    private void handlePredictPrice() {
        // parse the input values
        double houseAge = Double.parseDouble(houseAgeField.getText());
        double distanceToMRT = Double.parseDouble(distanceToMRTField.getText());
        int numConvenienceStores = Integer.parseInt(numConvenienceStoresField.getText());
        double latitude = Double.parseDouble(latitudeField.getText());
        double longitude = Double.parseDouble(longitudeField.getText());

        // train the model
        List<RealEstateRecord> realEstateRecords = realEstateData.loadRecords("data/real_estate_data.csv");
        regressionModel.trainModel(realEstateRecords);

        // predict the price
        double predictedPrice = regressionModel.predictPrice(houseAge, distanceToMRT, numConvenienceStores, latitude, longitude);

        // display the price
        predictedPriceField.setText(String.valueOf(predictedPrice));

        // save the input values and the predicted price to the database
        SaveToDatabase saveToDatabase = new SaveToDatabase();
        saveToDatabase.saveToDatabase(houseAge, distanceToMRT, numConvenienceStores, latitude, longitude, predictedPrice);
    }

    @FXML
    private void handleDataVisualization(ActionEvent event) {
        DataVisualization dataVisualization = new DataVisualization();
        dataVisualization.generateVisualizations("scripts/generate_visualizations.py");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Visualization.fxml"));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            Stage secondaryStage = new Stage();
            secondaryStage.getIcons().add(new Image("file:data/icon.jpg"));
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Data Visualization");
            secondaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
