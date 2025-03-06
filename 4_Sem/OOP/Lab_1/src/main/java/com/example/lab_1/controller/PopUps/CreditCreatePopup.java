package com.example.lab_1.controller.PopUps;

import com.example.lab_1.controller.CreditCreateController;
import com.example.lab_1.entities.Credit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class CreditCreatePopup {
    public static void showCreditCreatePopup(Stage parentStage, int userId, int bankId, Consumer<Credit> creditCreatedCallback) {
        try {
            FXMLLoader loader = new FXMLLoader(CreditCreatePopup.class.getResource("/com/example/lab_1/PopUps/CreditCreatePage.fxml"));
            Parent root = loader.load();

            CreditCreateController controller = loader.getController();
            controller.setUserAndBank(userId, bankId);
            controller.setCreditCreatedCallback(creditCreatedCallback);

            Stage popupStage = new Stage();
            popupStage.setTitle("Создание кредита");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(parentStage);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
