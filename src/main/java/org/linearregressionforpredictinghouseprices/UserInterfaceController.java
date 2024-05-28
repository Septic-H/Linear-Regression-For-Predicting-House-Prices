package org.linearregressionforpredictinghouseprices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.List;

public class UserInterfaceController {
    @FXML
    private TextField transactionDateField;

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
    private TextField predictedPriceField;

    private final RealEstateData realEstateData = new RealEstateData();
    private final LinearRegressionModel regressionModel = new LinearRegressionModel();

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
    }

    @FXML
    private void handleDataVisualization(ActionEvent event) {
        DataVisualization dataVisualization = new DataVisualization();
        dataVisualization.generateVisualizations("scripts/generate_visualizations.py");

//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Visualization.fxml"));
//            BorderPane visualizationPane = loader.load();
//            Scene visualizationScene = new Scene(visualizationPane);
//
//            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            currentStage.setScene(visualizationScene);
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
