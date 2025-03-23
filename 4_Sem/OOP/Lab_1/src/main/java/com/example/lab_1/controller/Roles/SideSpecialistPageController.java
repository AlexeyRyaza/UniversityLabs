package com.example.lab_1.controller.Roles;

import com.example.lab_1.Main;
import com.example.lab_1.controller.PopUps.EmployeeSelectionPopup;
import com.example.lab_1.entities.Enterprise;
import com.example.lab_1.entities.SalaryProject;
import com.example.lab_1.entities.Transfer;
import com.example.lab_1.entities.User;
import com.example.lab_1.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SideSpecialistPageController {
    @FXML
    private Label L_Title;

    @FXML
    private TextField TF_Amount;

    @FXML
    private TextField TF_Recipient;

    @FXML
    private TextField TF_TransferAmount;

    @FXML
    private Button btnCreateProject;

    @FXML
    private Button btnTransfer;

    @FXML
    private Button btnPayAll;

    @FXML
    private Button btnBack;

    @FXML
    protected void onBackButtonClicked() {
        Main.getInstance().showBankSelectionScene(userId);
    }

    // Non-FXML part
    private int userId;
    private int bankId;
    private int enterpriseId;
    private int corporateAccountId; // ID счета предприятия

    public void initData(int userId, int bankId, int enterpriseId) {
        this.userId = userId;
        this.bankId = bankId;
        this.enterpriseId = enterpriseId;

        Enterprise enterprise = EnterpriseService.getInstance().getEnterpriseById(String.valueOf(enterpriseId)).get();
        this.corporateAccountId = enterprise.getAccountId();

        SalaryProjectExist();
    }

    @FXML
    private void onCreateSalaryProject() {
        if(SalaryProjectExist()){
            return;
        }

        SalaryProject newProject = new SalaryProject.Builder()
                .enterpriseId(enterpriseId)
                .bankId(bankId)
                .approved(false)
                .build();

        SalaryProjectService.getInstance().saveSalaryProject(newProject);
        btnCreateProject.setText("Заявка отправлена");
        btnCreateProject.setDisable(true);
    }

    @FXML
    private void onTransfer() {
        int recipientId;
        int amount;

        try {
            recipientId = Integer.parseInt(TF_Recipient.getText());
            amount = Integer.parseInt(TF_TransferAmount.getText());
        } catch (NumberFormatException e) {
            return;
        }

        TransferService.getInstance().createSalaryTransfer(recipientId, amount, enterpriseId);
    }

    @FXML
    private void onPayAll() {
        Optional<SalaryProject> salaryProjectOpt = SalaryProjectService.getInstance()
                .getSalaryProjectByEnterpriseId(String.valueOf(enterpriseId));

        if (salaryProjectOpt.isEmpty()) {
            return;
        }
        SalaryProject salaryProject = salaryProjectOpt.get();
        List<Integer> users = SalaryProjectService.getInstance().getUsersOfSalaryProject(String.valueOf(salaryProject.getId()));
        List<Integer> accounts = new ArrayList<>();

        for (Integer userId : users) {
            var accountId = AccountService.getInstance().getAccountByBankIdAndUserId(String.valueOf(bankId), String.valueOf(userId));
            if(accountId != 0){
                accounts.add(accountId);
            }
        }

        accounts.forEach(account -> {
            TransferService.getInstance().createSalaryTransfer(account, 100, enterpriseId);;
        });
    }

    private boolean SalaryProjectExist() {
        var salaryPrj = SalaryProjectService.getInstance().getSalaryProjectByEnterpriseId(String.valueOf(enterpriseId));
        if(salaryPrj.isPresent() && salaryPrj.get().approved()){
            btnCreateProject.setText("Заявка одобрена");
            btnCreateProject.setDisable(true);
            return true;
        }

        return false;
    }

    @FXML
    private void onAddEmployee() {
        Stage currentStage = (Stage) btnCreateProject.getScene().getWindow();

        EmployeeSelectionPopup.showEmployeeSelectionPopup(currentStage, bankId, selectedUser -> {
            UserService.getInstance().assignUserToEnterprise(String.valueOf(selectedUser.getId()), String.valueOf(enterpriseId), 100);
        });
    }

}
