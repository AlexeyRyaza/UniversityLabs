package com.example.lab_1.controller.Roles;

import com.example.lab_1.Main;
import com.example.lab_1.entities.SalaryProject;
import com.example.lab_1.entities.Transfer;
import com.example.lab_1.services.SalaryProjectService;
import com.example.lab_1.services.TransferService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class OperatorPageController {
    @FXML
    private VBox VB_Transfers;

    @FXML
    private VBox VB_SalaryRequests;

    @FXML
    private Label L_Title;

    @FXML
    protected void onBackButtonClicked() {
        Main.getInstance().showBankSelectionScene(userId);
    }

    @FXML
    protected void onSaveButtonClicked() {

    }

    // Non-FXML part
    private int userId;
    private int bankId;

    public void initData(int userId, int bankId) {
        this.userId = userId;
        this.bankId = bankId;
        loadTransfers();
        loadSalaryRequests();
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
}
