package com.example.lab_1.controller;

import com.example.lab_1.Main;
import com.example.lab_1.controller.PopUps.CompanySelectionPopup;
import com.example.lab_1.controller.PopUps.RoleWaitingApprovePopup;
import com.example.lab_1.entities.Bank;
import com.example.lab_1.controller.PopUps.RoleSelectionPopup;
import com.example.lab_1.services.BankService;
import com.example.lab_1.services.EnterpriseService;
import com.example.lab_1.services.UserBankEnterpriseService;
import com.example.lab_1.services.UserBankService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BankSelectionController {
    @FXML
    private VBox BanksContainer;

    @FXML
    protected void onLoginBtnClicked(){
        Main.getInstance().showLoginScene();
    }

    //Non FXML part
    //==========================================================
    List<Bank> bankList = new ArrayList<>();
    int userId;

    private void showBanks(){
        for (Bank bank : bankList) {
            String role = UserBankService.getInstance().getUserRoleByID(String.valueOf(userId), String.valueOf(bank.getId()));

            if("side_specialist".equals(role)){
                int EnterpriseId = UserBankEnterpriseService.getInstance()
                        .getEnterpriseByUserAndBankID(String.valueOf(userId), String.valueOf(bank.getId()));

                var Enterprise= EnterpriseService.getInstance().getEnterpriseById(String.valueOf(EnterpriseId));

                role = Enterprise.isPresent() ? role +=  " of " + Enterprise.get().getName() : role;
            }

            Button bankButton = createBankButton(bank.getName() + "\nRole: " + ((role == null) ? "none" : role),
                    bank.getId());
            BanksContainer.getChildren().add(bankButton);
        }
    }

    private Button createBankButton(String buttonText, int bank_id) {
        Button button = new Button(buttonText);

        button.getStyleClass().add("bank-button");

        button.setOnAction(event -> {
            if(button.getText().endsWith("none")){
                RoleSelectionPopup.showRoleSelectionPopup((Stage) button.getScene().getWindow(), selectedRole ->{
                    selectedRole = selectedRole.toLowerCase();
                    selectedRole = selectedRole.replace(" ","_");

                    if("side_specialist".equals(selectedRole)){
                        Stage currentStage = (Stage) button.getScene().getWindow();

                        String finalSelectedRole = selectedRole;
                        CompanySelectionPopup.showCompanySelectionPopup(currentStage, String.valueOf(bank_id),selectedCompany -> {
                            if(selectedCompany == null){
                                return;
                            }

                            UserBankEnterpriseService.getInstance().saveInfo(String.valueOf(userId),
                                    String.valueOf(bank_id),
                                    String.valueOf(selectedCompany.getId()));

                            button.setText(buttonText.replace("none", finalSelectedRole
                                    + " of " + selectedCompany.getName()));

                            UserBankService.getInstance().saveRole(String.valueOf(userId), String.valueOf(bank_id),
                                    finalSelectedRole, true);
                        });



                        return;
                    }

                    UserBankService.getInstance().saveRole(String.valueOf(userId), String.valueOf(bank_id),
                            selectedRole, !"client".equals(selectedRole));

                    button.setText(buttonText.replace("none", selectedRole));
                });
            }
            else{
                int last_role_index = buttonText.lastIndexOf("Role: ");
                String rolePart = button.getText().substring(last_role_index + 6);
                String enterpriseName = "";

                if (rolePart.contains(" of ")) {
                     int ofIndex = rolePart.indexOf(" of ");
                     rolePart = rolePart.substring(0, ofIndex);

                     int last_space_index = button.getText().lastIndexOf(" ");
                     enterpriseName = button.getText().substring(last_space_index + 1);
                }

                var sceneBuilder = Main.getInstance();
                switch (rolePart){
                    case "client":
                        if(UserBankService.getInstance().isApproved(String.valueOf(userId), String.valueOf(bank_id))){
                            sceneBuilder.showClientScene(userId, bank_id);
                        }
                        else{
                            RoleWaitingApprovePopup.showPendingApprovalPopup((Stage) button.getScene().getWindow());
                        }
                        break;
                    case "operator":
                        sceneBuilder.showOperatorScene(userId, bank_id);
                        break;
                    case "manager":
                        sceneBuilder.showManagerScene(userId, bank_id);
                        break;
                    case "administrator":
                        sceneBuilder.showAdminScene(userId);
                        break;
                    case "side_specialist":
                        var enterpriseId = EnterpriseService.getInstance().getEnterpriseByName(enterpriseName).get().getId();
                        sceneBuilder.showSideSpecialistScene(userId, bank_id, enterpriseId);
                        break;
                    default:
                        break;
                }
            }
        });

        return button;
    }

    public void initData(int userId){
        this.userId = userId;

        bankList.addAll(BankService.getInstance().getAllBanks());
        bankList.sort(Comparator.comparing(Bank::getName));

        showBanks();
    }
}