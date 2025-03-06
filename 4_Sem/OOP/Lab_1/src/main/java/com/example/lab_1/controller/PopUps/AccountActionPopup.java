package com.example.lab_1.controller.PopUps;

import com.example.lab_1.controller.Account.AccountActionController;
import com.example.lab_1.entities.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class AccountActionPopup {
    public static void showAccountActionsPopup(Stage parentStage, Account account, Consumer<String> actionCallback) {
        try {
            FXMLLoader loader = new FXMLLoader(AccountActionPopup.class.getResource("/com/example/lab_1/PopUps/AccountActionPage.fxml"));
            Parent root = loader.load();

            AccountActionController controller = loader.getController();
            controller.setAccount(account);
            controller.setActionCallback(actionCallback);

            Stage popupStage = new Stage();
            popupStage.setTitle("Действия со счетом");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(parentStage);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
