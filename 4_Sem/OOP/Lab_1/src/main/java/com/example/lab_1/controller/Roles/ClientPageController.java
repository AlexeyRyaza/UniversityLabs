package com.example.lab_1.controller.Roles;

import com.example.lab_1.Main;
import com.example.lab_1.controller.PopUps.AccountCreatePopup;
import com.example.lab_1.controller.PopUps.CreditCreatePopup;
import com.example.lab_1.entities.*;
import com.example.lab_1.services.*;
import com.example.lab_1.controller.PopUps.AccountActionPopup;
import javafx.fxml.FXML;
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

        if(isAccountsLoaded){
            AccountCreatePopup.showAccountCreatePopup(currentStage, userId, bankId, newAccount -> {
                HBox accountItem = createAccountItem(newAccount);
                VB_Field.getChildren().add(accountItem);
            });
        }else {
            CreditCreatePopup.showCreditCreatePopup(currentStage, userId, bankId, newCredit -> {
                HBox creditItem = createCreditItem(newCredit);
                loadCredits();
            });
        }


    }

    @FXML
    protected void OnSalaryPrBTNClicked(){

    }

    @FXML
    protected void OnAccountsBTNClicked(){
        if(isAccountsLoaded) return;

        loadAccounts();
        isAccountsLoaded = true;
    }

    @FXML
    protected void OnCreditsBTNClicked(){
        if(!isAccountsLoaded) return;

        loadCredits();
        isAccountsLoaded = false;
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
    boolean isAccountsLoaded;

    private HBox createAccountItem(Account account) {
        HBox accountItem = new HBox(15);
        accountItem.getStyleClass().add("account-item");

        Button accountInfoBtn = new Button(
                "Id: " + account.getAccountId() +
                        "\nBalance: " + account.getBalance() + " " + account.getCurrency());
        accountInfoBtn.getStyleClass().add("account-info-button");
        accountInfoBtn.setOnAction(e -> {
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

                        Account destination = AccountService.getInstance()
                                .getAccountById(String.valueOf(destinationId)).orElse(null);
                        if (destination == null) return;
                        if(!account.getCurrency().equals(destination.getCurrency())) return;
                        if(account.getUserId() == destinationId) return;
                        if(account.getBalance() < amount) return;

                        TransferService.getInstance().createTransfer
                                (account.getAccountId(), destinationId, amount);

                        account.setBalance(account.getBalance() - amount);
                        accountInfoBtn.setText("Id: " + account.getAccountId() +
                                "\nBalance: " + account.getBalance() + " " + account.getCurrency());
                    }
                }
                else if(selectedAction.startsWith("withdraw:")){
                    String[] parts = selectedAction.split(":");
                    if(parts.length == 2){
                        int amount = Integer.parseInt(parts[1]);

                        if(account.getBalance() < amount){
                            return;
                        }

                        TransferService.getInstance().createTransfer(account.getAccountId(), amount);

                        account.setBalance(account.getBalance() - amount);
                        accountInfoBtn.setText("Id: " + account.getAccountId() +
                                "\nBalance: " + account.getBalance() + " " + account.getCurrency());
                    }

                }
                else if (selectedAction.equals("delete")) {
                    System.out.println(selectedAction);
                }
                else if (selectedAction.equals("freeze")) {
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
        isAccountsLoaded = true;

        loadSalaryProject();
    }

    private void loadSalaryProject() {
        int salaryProjectId = UserService.getInstance().getUsersSalaryProject(String.valueOf(userId));
        if(salaryProjectId == -1){
            L_SalaryPr.setText("None");
        }
        else {
            SalaryProject salaryProject = SalaryProjectService.getInstance()
                    .getSalaryProjectById(String.valueOf(salaryProjectId)).get();

            Enterprise enterprise = EnterpriseService.getInstance()
                    .getEnterpriseById(String.valueOf(salaryProject.getEnterpriseId())).get();

            L_SalaryPr.setText(enterprise.getName());
        }
    }

    private void loadAccounts(){
        List<Account> allAccounts = AccountService.getInstance().getAccountsByUserId(String.valueOf(userId));
        List<Account> accounts = allAccounts.stream()
                .filter(acc -> acc.getBankId() == bankId)
                .toList();

        VB_Field.getChildren().clear();

        for (Account account : accounts) {
            VB_Field.getChildren().add(createAccountItem(account));
        }
    }

    private void loadCredits(){
        List<Credit> allCredits = CreditService.getInstance().getCreditsByUserId(String.valueOf(userId));
        List<Credit> credits = allCredits.stream()
                .filter(credit -> credit.getBankId() == bankId)
                .toList();

        VB_Field.getChildren().clear();
        for (Credit credit : credits) {
            HBox creditItem = createCreditItem(credit);
            VB_Field.getChildren().add(creditItem);
        }
    }

    private HBox createCreditItem(Credit credit) {
        HBox creditItem = new HBox(15);
        creditItem.getStyleClass().add("credit-item");

        if (!credit.isApproved()) {
            creditItem.getStyleClass().add("credit-not-approved");
        }

        Button creditInfoBtn = new Button(
                        "\nAmount: " + credit.getAmount() +
                        "\nRate: " + credit.getInterestRate() +
                        "\nTerm: " + credit.getTerm() + " months" +
                        "\nMonthly Payment: " + credit.getMonthlyPayment());
        creditInfoBtn.getStyleClass().add("credit-info-button");
        creditInfoBtn.setOnAction(e -> {
            // Обработка нажатия для просмотра информации по кредиту
            System.out.println("Просмотр кредита: " + credit.getId());
        });

        creditItem.getChildren().addAll(creditInfoBtn);
        return creditItem;
    }
}
