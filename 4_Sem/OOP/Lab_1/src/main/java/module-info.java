module com.example.lab_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.lab_1 to javafx.fxml;
    exports com.example.lab_1;
    exports com.example.lab_1.controller;
    opens com.example.lab_1.controller to javafx.fxml;
}