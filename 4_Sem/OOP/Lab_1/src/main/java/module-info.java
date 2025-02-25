module com.example.lab_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    opens com.example.lab_1.entities to com.fasterxml.jackson.databind, com.couchbase.client.core;

    requires com.dlsc.formsfx;
    requires com.couchbase.client.java;
    requires com.couchbase.client.core;

    opens com.example.lab_1 to javafx.fxml;
    exports com.example.lab_1;
    exports com.example.lab_1.controller;
    opens com.example.lab_1.controller to javafx.fxml;
}