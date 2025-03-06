package com.example.lab_1.controller.Account;

import com.example.lab_1.entities.Account;
import com.example.lab_1.services.AccountService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class AccountCreateController {
    @FXML
    private TextField TF_Amount;

    @FXML
    private TextField TF_Currency;

    private Consumer<Account> accountCreatedCallback;

    private int userId;
    private int bankId;

    public void setUserAndBank(int userId, int bankId) {
        this.userId = userId;
        this.bankId = bankId;
    }

    public void setAccountCreatedCallback(Consumer<Account> callback) {
        this.accountCreatedCallback = callback;
    }

    @FXML
    private void onCreateAccountClicked() {
        try {
            double amount = Double.parseDouble(TF_Amount.getText().trim());
            String currency = TF_Currency.getText().trim();

            Account newAccount = new Account.Builder()
                    .userId(userId)
                    .bankId(bankId)
                    .balance(amount)
                    .currency(currency)
                    .build();

            AccountService.getInstance().saveAccount(newAccount);

            if (accountCreatedCallback != null) {
                accountCreatedCallback.accept(newAccount);
            }

            closeWindow();
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат суммы");
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancelClicked() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) TF_Amount.getScene().getWindow();
        stage.close();
    }
}
