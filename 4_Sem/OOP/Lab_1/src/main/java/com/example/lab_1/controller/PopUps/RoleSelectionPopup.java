package com.example.lab_1.controller.PopUps;

import com.example.lab_1.controller.RoleSelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class RoleSelectionPopup {
    public static void showRoleSelectionPopup(Stage parentStage, Consumer<String> roleCallback) {
        try {
            FXMLLoader loader = new FXMLLoader(RoleSelectionPopup.class.getResource("/com/example/lab_1/PopUps/RoleSelectionPage.fxml"));
            Parent root = loader.load();

            RoleSelectionController controller = loader.getController();
            controller.setRoleCallback(roleCallback);

            Stage popupStage = new Stage();
            popupStage.setTitle("Выбор роли");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(parentStage);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
