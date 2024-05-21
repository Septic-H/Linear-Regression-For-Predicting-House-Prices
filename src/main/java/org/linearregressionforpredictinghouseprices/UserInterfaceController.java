package org.linearregressionforpredictinghouseprices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UserInterfaceController {
    @FXML
    private void handlePredictPrice() {
        // Implementation for price prediction
    }

    @FXML
    private void handleDataVisualization() {
        // Implementation for data visualization
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
