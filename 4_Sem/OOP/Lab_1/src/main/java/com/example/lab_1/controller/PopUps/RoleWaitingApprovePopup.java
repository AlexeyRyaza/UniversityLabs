package com.example.lab_1.controller.PopUps;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RoleWaitingApprovePopup extends Stage {
    public static void showPendingApprovalPopup(Stage parentStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Ожидание одобрения");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10;");

        Label message = new Label("Ваша заявка еще не одобрена, подождите пожалуйста.");
        message.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-alignment: center;");

        Button closeButton = new Button("Закрыть");
        closeButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 12px;");
        closeButton.setOnAction(e -> popupStage.close());

        layout.getChildren().addAll(message, closeButton);

        Scene scene = new Scene(layout, 300, 120);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
