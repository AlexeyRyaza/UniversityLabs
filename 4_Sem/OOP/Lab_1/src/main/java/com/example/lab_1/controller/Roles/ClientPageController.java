package com.example.lab_1.controller.Roles;

import com.example.lab_1.Main;
import com.example.lab_1.controller.PopUps.AccountCreatePopup;
import com.example.lab_1.entities.Account;
import com.example.lab_1.entities.Transfer;
import com.example.lab_1.entities.User;
import com.example.lab_1.services.AccountService;
import com.example.lab_1.services.TransferService;
import com.example.lab_1.services.UserService;
import com.example.lab_1.controller.PopUps.AccountActionPopup;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ClientPageController {
    @FXML
    Label L_Name;

    @FXML
    Label L_SalaryPr;

    @FXML
    VBox VB_Field;

    @FXML
    protected void OnCreateAccountBTNClicked(){
        Stage currentStage = (Stage) VB_Field.getScene().getWindow();

        AccountCreatePopup.showAccountCreatePopup(currentStage, userId, bankId, newAccount -> {
            HBox accountItem = createAccountItem(newAccount);
            VB_Field.getChildren().add(accountItem);
        });
    }

    @FXML
    protected void OnSalaryPrBTNClicked(){

    }

    @FXML
    protected void OnAccountsBTNClicked(){

    }

    @FXML
    protected void OnCreditsBTNClicked(){

    }

    @FXML
    protected void OnBackBTNClicked(){
        Main.getInstance().showBankSelectionScene(userId);
    }

    //Non Fxml part
    //=========================================================
    int userId;
    int bankId;
    User user;

    private HBox createAccountItem(Account account) {
        HBox accountItem = new HBox(15);
        accountItem.getStyleClass().add("account-item");

        Button accountInfoBtn = new Button(
                "Id: " + account.getAccountId() +
                        "\nBalance: " + account.getBalance() + " " + account.getCurrency());
        accountInfoBtn.getStyleClass().add("account-info-button");
        accountInfoBtn.setOnAction(e -> {
            //TODO Watch all info
        });

        Button actionBtn = new Button("Actions");
        actionBtn.getStyleClass().add("account-action-button");
        actionBtn.setOnAction(e -> {
            Stage stage = (Stage) actionBtn.getScene().getWindow();
            AccountActionPopup.showAccountActionsPopup(stage, account, selectedAction -> {
                if (selectedAction.startsWith("transfer:")) {
                    String[] parts = selectedAction.split(":");
                    if (parts.length == 3) {
                        int destinationId = Integer.parseInt(parts[1]);
                        int amount = Integer.parseInt(parts[2]);

                        if(!account.getCurrency().equals
                                (AccountService.getInstance().getAccountById(String.valueOf(destinationId))
                                .get().getCurrency())){

                            return;
                        }

                        if(account.getBalance() < amount){
                            return;
                        }



                        TransferService.getInstance().createTransfer
                                (account.getAccountId(), destinationId, amount);


                        account.setBalance(account.getBalance() - amount);
                        accountInfoBtn.setText("Id: " + account.getAccountId() +
                                "\nBalance: " + account.getBalance() + " " + account.getCurrency());
                    }
                } else if (selectedAction.equals("delete")) {
                    System.out.println(selectedAction);
                } else if (selectedAction.equals("freeze")) {
                    System.out.println(selectedAction);
                }

            });
        });

        accountItem.getChildren().addAll(accountInfoBtn, actionBtn);
        return accountItem;
    }

    public void initData(int userId, int bankId){
        this.userId = userId;
        this.bankId = bankId;

        user = UserService.getInstance()
                .getUserById(String.valueOf(userId))
                .orElse(null);

        if(user != null){
            L_Name.setText(user.getLastName() + " " + user.getFirstName());
        }
        loadAccounts();
    }

    private void loadAccounts(){
        List<Account> allAccounts = AccountService.getInstance().getAccountsByUserId(String.valueOf(userId));
        List<Account> accounts = allAccounts.stream()
                .filter(acc -> acc.getBankId() == bankId)
                .toList();

        VB_Field.getChildren().clear();

        for (Account account : accounts) {
            var accountItem = createAccountItem(account);

            VB_Field.getChildren().add(accountItem);
        }
    }
}
