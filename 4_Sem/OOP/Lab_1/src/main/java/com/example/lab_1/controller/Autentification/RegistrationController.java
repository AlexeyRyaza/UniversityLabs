package com.example.lab_1.controller.Autentification;

import com.example.lab_1.Main;
import com.example.lab_1.services.UserService;
import com.example.lab_1.infrastructure.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {
    @FXML
    private TextField TF_Number;

    @FXML
    private PasswordField TF_Password;

    @FXML
    private PasswordField TF_ConfirmPassword;

    @FXML
    private Label L_Redirection;

    @FXML
    private Label L_Error;

    @FXML
    protected void onSignUpBtnClicked(){
        String PassportNumber = TF_Number.getText();
        String Password = TF_Password.getText();
        String ConfirmPassword = TF_ConfirmPassword.getText();

        if(!Validator.isValidPassport(PassportNumber)){
            L_Error.setText("Passport number is invalid");
            TF_Number.clear();
            return;
        }

        if (!Password.equals(ConfirmPassword)) {
            L_Error.setText("Passwords do not match");
            TF_Password.clear();
            TF_ConfirmPassword.clear();
            return;
        }

        if (PassportNumber.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty()) {
            L_Error.setText("All fields must be filled");
            return;
        }

        if(UserService.getInstance().isUserExistByPassport(PassportNumber)){
            L_Error.setText("User already exist");
            return;
        }

        Main.getInstance().showFinishRegistrationScene(PassportNumber, Password);
    }

    @FXML
    protected void onLoginBtnClicked(){
        Main.getInstance().showLoginScene();
    }

}
