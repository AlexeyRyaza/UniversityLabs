package com.example.lab_1.controller;

import com.example.lab_1.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void ABOBA_BTN(){
        Main.getInstance().showRegistrationScene();
    }
}