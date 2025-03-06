package com.example.lab_1.controller.Account;

import com.example.lab_1.entities.Account;
import com.example.lab_1.services.AccountService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class AccountActionController {
    @FXML private Button transferButton;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;
    @FXML private Button freezeButton;
    @FXML private TextField TF_Destination;
    @FXML private TextField TF_Amount;


    private Consumer<String> actionCallback;
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setActionCallback(Consumer<String> actionCallback) {
        this.actionCallback = actionCallback;
    }

    @FXML
    public void initialize(){
        transferButton.setOnAction(e -> {
            if (TF_Amount.getText() == null || TF_Destination.getText() == null) {
                return;
            }

            if (!TF_Amount.getText().matches("\\d+")) {
                return;
            }
            String destinationText = TF_Destination.getText();
            if (!destinationText.matches("\\d+")) {
                return;
            }

            int amount = Integer.parseInt(TF_Amount.getText());
            var destination = AccountService.getInstance().getAccountById(destinationText);
            if (destination.isEmpty()) {
                return;
            }

            String result = "transfer:" + destination.get().getAccountId() + ":" + amount;

            if (actionCallback != null) {
                actionCallback.accept(result);
            }
            closeWindow();
        });
        deleteButton.setOnAction(e -> {
            if(actionCallback != null){
                actionCallback.accept("delete");
            }
            closeWindow();
        });
        freezeButton.setOnAction(e -> {
            if(actionCallback != null){
                actionCallback.accept("freeze");
            }
            closeWindow();
        });
        cancelButton.setOnAction(e -> closeWindow());
    }

    private void closeWindow(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
