package com.example.lab_1.controller;

import com.example.lab_1.Main;
import com.example.lab_1.entities.Bank;
import com.example.lab_1.entities.User;
import com.example.lab_1.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class BankSelectionController {
    @FXML
    private VBox BanksContainer;

    @FXML
    protected void onLoginBtnClicked(){
        Main.getInstance().showLoginScene();
    }

    @FXML
    protected void onNewBankBtnClicked(){
        bankList.add(new Bank("Aboba", "123213", "Tam"));

        addBank();
    }


    //Non FXML part
    //==========================================================
    List<Bank> bankList = new ArrayList<Bank>();

    private void addBank(){
        BanksContainer.getChildren().add(createBankButton("Bank"));
    }

    public void showBanks(){
        for (Bank bank : bankList) {
            Button bankButton = createBankButton(bank.getName());
            BanksContainer.getChildren().add(bankButton);
        }
    }

    private Button createBankButton(String buttonText) {
        UserService.getInstance().getAllUsers().forEach(user -> {
            System.out.println(user.getFirstName() + " " + user.getPhone());
        });

        UserService.getInstance().getUserById("2").ifPresent(user -> {
            System.out.println(user.getFirstName() + " " + user.getEmail());
        });



        Button button = new Button(buttonText);

        button.getStyleClass().add("bank-button");

        button.setOnAction(event -> {
            System.out.println("Выбран банк: " + buttonText);
        });

        return button;
    }
}
