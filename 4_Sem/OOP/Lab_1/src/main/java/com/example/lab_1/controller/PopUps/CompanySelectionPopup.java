package com.example.lab_1.controller.PopUps;

import com.example.lab_1.controller.CompanySelectionController;
import com.example.lab_1.entities.Enterprise;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class CompanySelectionPopup {
    public static void showCompanySelectionPopup(Stage parentStage, String bankId, Consumer<Enterprise> companySelectedCallback) {
        try {
            FXMLLoader loader = new FXMLLoader(CompanySelectionPopup.class.getResource("/com/example/lab_1/PopUps/CompanySelectionPage.fxml"));
            Parent root = loader.load();

            CompanySelectionController controller = loader.getController();
            controller.initData(bankId);
            controller.setCompanySelectedCallback(companySelectedCallback);

            Stage popupStage = new Stage();
            popupStage.setTitle("Выбор компании");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(parentStage);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
