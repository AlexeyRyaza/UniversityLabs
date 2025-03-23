package com.example.lab_1.controller.Roles;

import com.example.lab_1.Main;
import com.example.lab_1.controller.PopUps.CreditApprovalPopup;
import com.example.lab_1.controller.PopUps.UserApprovalPopup;
import com.example.lab_1.entities.Credit;
import com.example.lab_1.entities.SalaryProject;
import com.example.lab_1.entities.Transfer;
import com.example.lab_1.entities.User;
import com.example.lab_1.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

public class ManagerPageController {
    @FXML private VBox VB_Enterprises;
    @FXML private VBox VB_Transfers;
    @FXML private VBox VB_SalaryRequests;
    @FXML private VBox VB_Loans;
    @FXML private VBox VB_ClientsApprove;
    @FXML private Label L_Title;


    private int userId;
    private int bankId;

    @FXML
    protected void onBackButtonClicked() {
        Main.getInstance().showBankSelectionScene(userId);
    }

    public void initData(int userId, int bankId) {
        this.userId = userId;
        this.bankId = bankId;

        loadTransfers();
        loadSalaryRequests();
        loadEnterpriseTransfers();
        loadCreditRequests();
        loadUserApprovalRequests();
    }

    private void loadTransfers() {
        VB_Transfers.getChildren().clear();

        List<Transfer> transfers = TransferService.getInstance().getTransfersByBank(String.valueOf(bankId));

        for (Transfer transfer : transfers) {
            HBox transferItem = createTransferItem(transfer);
            VB_Transfers.getChildren().add(transferItem);
        }
    }

    private HBox createTransferItem(Transfer transfer) {
        HBox transferItem = new HBox(15);
        transferItem.getStyleClass().add("transfer-item");

        Label transferInfo = new Label(
                "ID: " + transfer.getId() +
                        "\nОтправитель: " + transfer.getSourceAccount() +
                        "\nПолучатель: " + transfer.getDestinationAccount() +
                        "\nСумма: " + transfer.getAmount()
        );
        transferInfo.getStyleClass().add("transfer-info");

        Button cancelButton = new Button("Отменить");
        cancelButton.getStyleClass().add("cancel-button");
        cancelButton.setOnAction(event -> cancelTransfer(transfer));

        transferItem.getChildren().addAll(transferInfo, cancelButton);
        return transferItem;
    }

    private void cancelTransfer(Transfer transfer) {
        TransferService.getInstance().createTransfer(transfer.getDestinationAccount(),
                transfer.getSourceAccount(),
                transfer.getAmount());

        loadTransfers();
    }

    private void loadSalaryRequests() {
        VB_SalaryRequests.getChildren().clear();

        List<SalaryProject> requests = SalaryProjectService.getInstance().getPendingApprovals(String.valueOf(bankId));

        for (SalaryProject request : requests) {
            HBox requestItem = createSalaryRequestItem(request);
            VB_SalaryRequests.getChildren().add(requestItem);
        }
    }

    private HBox createSalaryRequestItem(SalaryProject request) {
        HBox requestItem = new HBox(15);
        requestItem.getStyleClass().add("salary-item");

        Label requestInfo = new Label("Компания: " + request.getEnterpriseId());
        requestInfo.getStyleClass().add("salary-info");

        Button approveButton = new Button("Одобрить");
        approveButton.getStyleClass().add("salary-button");
        approveButton.setOnAction(event -> approveSalaryProject(request));

        requestItem.getChildren().addAll(requestInfo, approveButton);
        return requestItem;
    }

    private void approveSalaryProject(SalaryProject request) {
        SalaryProjectService.getInstance().approveSalaryProject(String.valueOf(request.getId()));
        loadSalaryRequests();
    }

    private void loadEnterpriseTransfers() {
        VB_Enterprises.getChildren().clear();

        List<Transfer> enterpriseTransfers = TransferService.getInstance().
                getTransfersByBankAndEnterprise(String.valueOf(bankId));

        enterpriseTransfers.sort(Comparator.comparing(e -> e.getId()));

        for (Transfer transfer : enterpriseTransfers) {
            HBox transferItem = createEnterpriseTransferItem(transfer);
            VB_Enterprises.getChildren().add(transferItem);
        }
    }

    private HBox createEnterpriseTransferItem(Transfer transfer) {
        HBox transferItem = new HBox(15);
        transferItem.getStyleClass().add("transfer-item");

        Label transferInfo = new Label(
                "ID: " + transfer.getId() +
                        "\nОтправитель (предприятие): " + -transfer.getSourceAccount() +
                        "\nПолучатель: " + transfer.getDestinationAccount() +
                        "\nСумма: " + transfer.getAmount()
        );
        transferInfo.getStyleClass().add("transfer-info");

        Button cancelButton = new Button("Отменить");
        cancelButton.getStyleClass().add("cancel-button");
        cancelButton.setOnAction(event -> cancelEnterpriseTransfer(transfer));

        transferItem.getChildren().addAll(transferInfo, cancelButton);
        return transferItem;
    }

    private void cancelEnterpriseTransfer(Transfer transfer) {
        TransferService.getInstance().createTransfer(
                transfer.getDestinationAccount(),
                transfer.getSourceAccount(),
                transfer.getAmount()
        );

        loadEnterpriseTransfers();
    }

    private void loadCreditRequests() {
        VB_Loans.getChildren().clear();

        List<Credit> requests = CreditService.getInstance().getPendingCredits(String.valueOf(bankId));

        for (Credit request : requests) {
            HBox requestItem = createCreditRequestItem(request);
            VB_Loans.getChildren().add(requestItem);
        }
    }

    private HBox createCreditRequestItem(Credit request) {
        HBox requestItem = new HBox(15);
        requestItem.getStyleClass().add("credit-item");

        Label requestInfo = new Label(
                "Клиент: " + request.getUserId() +
                        " | Сумма: " + request.getAmount() +
                        " | Процент: " + request.getInterestRate() + "%"
        );
        requestInfo.getStyleClass().add("credit-info");

        Button detailsButton = new Button("Подробнее");
        detailsButton.getStyleClass().add("credit-button");
        detailsButton.setOnAction(event -> showCreditApprovalPopup(request));

        requestItem.getChildren().addAll(requestInfo, detailsButton);
        return requestItem;
    }

    private void showCreditApprovalPopup(Credit request) {
        Stage currentStage = (Stage) VB_Loans.getScene().getWindow();

        CreditApprovalPopup.showCreditApprovalPopup(currentStage, request, decision -> {
            if (decision) {
                System.out.println("Кредит одобрен: " + request.getId());
            } else {
                System.out.println("Кредит отклонен: " + request.getId());
            }
            loadCreditRequests();
        });
    }

    private void loadUserApprovalRequests() {
        VB_ClientsApprove.getChildren().clear();

        List<User> pendingUsers = UserBankService.getInstance().getPendingUsers(String.valueOf(bankId));

        for (User user : pendingUsers) {
            HBox userItem = createUserApprovalItem(user);
            VB_ClientsApprove.getChildren().add(userItem);
        }
    }

    private HBox createUserApprovalItem(User user) {
        HBox userItem = new HBox(15);
        userItem.getStyleClass().add("user-item");

        Label userInfo = new Label(
                "ФИО: " + user.getLastName() + " " + user.getFirstName() +
                        " | Email: " + user.getEmail()
        );
        userInfo.getStyleClass().add("user-info");

        Button detailsButton = new Button("Подробнее");
        detailsButton.getStyleClass().add("user-button");
        detailsButton.setOnAction(event -> showUserApprovalPopup(user));

        userItem.getChildren().addAll(userInfo, detailsButton);
        return userItem;
    }

    private void showUserApprovalPopup(User user) {
        Stage currentStage = (Stage) VB_ClientsApprove.getScene().getWindow();

        UserApprovalPopup.showUserApprovalPopup(currentStage, user, decision -> {
            if (decision) {
                UserBankService.getInstance().approveUser(String.valueOf(user.getId()), String.valueOf(bankId));
            } else {
                UserBankService.getInstance().rejectUser(String.valueOf(user.getId()), String.valueOf(bankId));
            }
            loadUserApprovalRequests();
        });
    }
}
