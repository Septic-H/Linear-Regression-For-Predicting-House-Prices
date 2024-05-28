module org.linearregressionforpredictinghouseprices {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.linearregressionforpredictinghouseprices to javafx.fxml;
    exports org.linearregressionforpredictinghouseprices;
}