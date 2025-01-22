module edu.smc.finalproject.finalproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens edu.smc.finalproject.finalproject to javafx.fxml;
    exports edu.smc.finalproject.finalproject;
}