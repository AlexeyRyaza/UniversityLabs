package com.example.lab_1.controller;

import com.example.lab_1.entities.Enterprise;
import com.example.lab_1.services.EnterpriseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class CompanySelectionController {
    @FXML
    private VBox companyListContainer;

    @FXML
    private Button confirmButton;

    private Consumer<Enterprise> companySelectedCallback;
    private Enterprise selectedCompany = null;
    private String bankId;

    public void setCompanySelectedCallback(Consumer<Enterprise> callback) {
        this.companySelectedCallback = callback;
    }

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            if (selectedCompany != null && companySelectedCallback != null) {
                companySelectedCallback.accept(selectedCompany);
            }
            closeWindow();
        });
    }

    public void initData(String bankId){
        this.bankId = bankId;
        loadCompanies();
    }

    private void loadCompanies() {
        List<Enterprise> enterprises = EnterpriseService.getInstance().getEnterprisesByBankId(bankId);

        for (Enterprise enterprise : enterprises) {
            Button companyButton = new Button(enterprise.getName());
            companyButton.getStyleClass().add("role-button");

            companyButton.setOnAction(event -> {
                selectedCompany = enterprise;
                highlightSelectedCompany(companyButton);
            });

            companyListContainer.getChildren().add(companyButton);
        }
    }

    private void highlightSelectedCompany(Button selectedButton) {
        for (var node : companyListContainer.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().remove("selected-button");
            }
        }
        selectedButton.getStyleClass().add("selected-button");
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
