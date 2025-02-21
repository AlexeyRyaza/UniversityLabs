package com.example.lab_1.controller;

import com.example.lab_1.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InitialController {
    @FXML
    private Label WelcomeLabel;

    @FXML
    protected void onLoginBtnClicked(){
        Main.getInstance().showLoginScene();
    }
    @FXML
    protected void onSignUpBtnClicked(){
        Main.getInstance().showRegistrationScene();
    }
}
