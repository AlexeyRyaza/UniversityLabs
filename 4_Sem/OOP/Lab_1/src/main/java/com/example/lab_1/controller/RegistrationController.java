package com.example.lab_1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegistrationController {
    @FXML
    private Label AYAYA_LAbel;

    @FXML
    private Label SomeText;

    @FXML
    protected void onAYAYAClick(){
        SomeText.setText(AYAYA_LAbel.getText());
    }
}
