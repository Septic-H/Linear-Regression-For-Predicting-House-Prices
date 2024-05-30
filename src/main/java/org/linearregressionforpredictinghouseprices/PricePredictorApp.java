package org.linearregressionforpredictinghouseprices;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class PricePredictorApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInterface.fxml"));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
            primaryStage.getIcons().add(new Image("file:data/icon.jpg"));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Linear Regression for Predicting House Prices");
            primaryStage.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
