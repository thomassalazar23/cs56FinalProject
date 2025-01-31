module edu.smc.finalproject.finalproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    opens FurnitureApp.FurnitureInfo to javafx.base;
    opens FurnitureApp.CustomerInfo to javafx.fxml;

    opens GUI to javafx.fxml;
    exports GUI;
}