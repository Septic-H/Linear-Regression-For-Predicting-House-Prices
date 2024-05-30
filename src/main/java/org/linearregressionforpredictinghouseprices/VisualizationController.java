package org.linearregressionforpredictinghouseprices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class VisualizationController {

    @FXML
    public ImageView imageView;

    private List<String> imagePaths = new ArrayList<>();

    public VisualizationController() {
        imagePaths.add("file:visuals/Y_vs_X1.png");
        imagePaths.add("file:visuals/Y_vs_X2.png");
        imagePaths.add("file:visuals/Y_vs_X3.png");
        imagePaths.add("file:visuals/Y_vs_X4.png");
        imagePaths.add("file:visuals/Y_vs_X5.png");
        imagePaths.add("file:visuals/Y_vs_X6.png");
        imagePaths.add("file:visuals/correlation_heatmap.png");
    }

    @FXML
    private void initialize() {
        if (!imagePaths.isEmpty()) {
            loadImage(imagePaths.get(0));
        }
    }

    private void loadImage(String imagePath) {
        try {
            Image image = new Image(imagePath);

            Rectangle clip = new Rectangle(800, 600);
            imageView.setClip(clip);

            imageView.setPreserveRatio(true);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);

            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showNextImage() {
        if (!imagePaths.isEmpty()) {
            int currentIndex = imagePaths.indexOf(imageView.getImage().getUrl());
            int nextIndex = (currentIndex + 1) % imagePaths.size();
            loadImage(imagePaths.get(nextIndex));
        }
    }

    @FXML
    private void showPreviousImage() {
        // Display the previous image in the list
        if (!imagePaths.isEmpty()) {
            int currentIndex = imagePaths.indexOf(imageView.getImage().getUrl());
            int previousIndex = (currentIndex - 1 + imagePaths.size()) % imagePaths.size();
            loadImage(imagePaths.get(previousIndex));
        }
    }

    @FXML
    public void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
