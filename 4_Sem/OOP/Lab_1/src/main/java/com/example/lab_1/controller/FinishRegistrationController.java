package com.example.lab_1.controller;

import com.example.lab_1.Main;
import com.example.lab_1.entities.User;
import com.example.lab_1.services.UserService;
import com.example.lab_1.services.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class FinishRegistrationController {
    @FXML
    private Label L_Error;

    @FXML
    private Label L_TellAbout;

    @FXML
    private Label L_Redirection;

    @FXML
    private TextField TF_FirstName;

    @FXML
    private TextField TF_LastName;

    @FXML
    private TextField TF_FatherName;

    @FXML
    private TextField TF_Email;

    @FXML
    private TextField TF_PhoneNumber;

    private String password;
    private String passportNumber;

    @FXML
    protected void onFinishBtnClicked(){
        if(!Validator.isValidName(TF_FirstName.getText())){
            L_Error.setText("First name is invalid");
            return;
        }
        if(!Validator.isValidName(TF_LastName.getText())){
            L_Error.setText("Last name is invalid");
            return;
        }
        if(!Validator.isValidName(TF_FatherName.getText()) && TF_FatherName == null){
            L_Error.setText("Father name is invalid");
            return;
        }

        if(!Validator.isValidEmail(TF_Email.getText())){
            L_Error.setText("Email is invalid");
            return;
        }
        if(!Validator.isValidPhoneNumber(TF_PhoneNumber.getText())){
            L_Error.setText("Phone number is invalid");
            return;
        }
        if(TF_FirstName.getText().isEmpty() || TF_LastName.getText().isEmpty() || TF_PhoneNumber.getText().isEmpty()
                || TF_Email.getText().isEmpty()){
            L_Error.setText("Fields must be filled");
            return;
        }

        UserService.getInstance().saveUser(new User.Builder()
                .lastName(TF_LastName.getText())
                .firstName(TF_FirstName.getText())
                .fatherName(TF_FatherName.getText())
                .email(TF_Email.getText())
                .passport(passportNumber)
                .phone(TF_PhoneNumber.getText())
                .password(password)
                .build());

        Main.getInstance().showBankSelectionScene();
    }

    @FXML
    protected void onLoginBtnClicked(){
        Main.getInstance().showLoginScene();
    }

    public void initData(String passport, String password){
        this.password = password;
        this.passportNumber = passport;
    }
}
