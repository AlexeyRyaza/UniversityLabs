package com.example.lab_1.controller.Autentification;

import com.example.lab_1.Main;
import com.example.lab_1.services.UserService;
import com.example.lab_1.infrastructure.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField TF_Number;

    @FXML
    private PasswordField TF_Password;

    @FXML
    private Label L_Error;

    @FXML
    private Label L_Redirection;

    @FXML
    protected void onLoginBtnClicked(){
        if(TF_Number.getText().isEmpty() || TF_Password.getText().isEmpty()){
            L_Error.setText("Fields cannot be empty");
            return;
        }

        if(!Validator.isValidPhoneNumber(TF_Number.getText())){
            L_Error.setText("Invalid Phone Number");
            return;
        }

        if(!UserService.getInstance().isUserExistByPhone(TF_Number.getText())){
            L_Error.setText("User not found");
            return;
        }

        if(!UserService.getInstance().getUserPassword(TF_Number.getText()).equals(TF_Password.getText())){
            L_Error.setText("Wrong Password");
            return;
        }

        var user = UserService.getInstance().getUserByPhoneNumber(TF_Number.getText());
        user.ifPresent(value -> Main.getInstance().showBankSelectionScene(value.getId()));
    }
    @FXML
    protected void onSignUpBtnClicked(){
        Main.getInstance().showRegistrationScene();
    }
}