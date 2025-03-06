package com.example.lab_1.controller.PopUps;

import com.example.lab_1.controller.Account.AccountCreateController;
import com.example.lab_1.entities.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class AccountCreatePopup {
    public static void showAccountCreatePopup(Stage parentStage, int userId, int bankId, Consumer<Account> accountCreatedCallback) {
        try {
            FXMLLoader loader = new FXMLLoader(AccountCreatePopup.class.getResource("/com/example/lab_1/PopUps/AccountCreatePage.fxml"));
            Parent root = loader.load();

            AccountCreateController controller = loader.getController();
            controller.setUserAndBank(userId, bankId);
            controller.setAccountCreatedCallback(accountCreatedCallback);

            Stage popupStage = new Stage();
            popupStage.setTitle("Создать новый счет");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(parentStage);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
