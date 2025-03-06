package com.example.lab_1.controller;

import com.example.lab_1.Main;
import com.example.lab_1.entities.Bank;
import com.example.lab_1.controller.PopUps.RoleSelectionPopup;
import com.example.lab_1.services.BankService;
import com.example.lab_1.services.UserBankService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
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

                    UserBankService.getInstance().saveRole(String.valueOf(userId), String.valueOf(bank_id), selectedRole);

                    button.setText(buttonText.replace("none", selectedRole));
                });
            }
            else{
                int last_space_index = buttonText.lastIndexOf(" ");
                String role = button.getText().substring(last_space_index + 1);

                switch (role){
                    case "client":
                        Main.getInstance().showClientScene(userId, bank_id);
                        break;
                    case "operator":

                        break;
                    case "manager":

                        break;
                    case "administrator":

                        break;
                    case "side_specialist":

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
        showBanks();
    }
}