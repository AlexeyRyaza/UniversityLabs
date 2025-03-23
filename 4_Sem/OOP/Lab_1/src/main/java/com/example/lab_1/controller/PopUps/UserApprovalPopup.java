package com.example.lab_1.controller.PopUps;

import com.example.lab_1.entities.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class UserApprovalPopup {
    public static void showUserApprovalPopup(Stage parentStage, User user, Consumer<Boolean> onDecision) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Одобрение пользователя");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10;");

        Label titleLabel = new Label("Запрос на регистрацию:");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label details = new Label(
                "ФИО: " + user.getLastName() + " " + user.getFirstName() + "\n" +
                        "Email: " + user.getEmail() + "\n" +
                        "Телефон: " + user.getPhone()
        );

        Button approveButton = new Button("Одобрить");
        approveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 12px;");
        approveButton.setOnAction(e -> {
            onDecision.accept(true);
            popupStage.close();
        });

        Button rejectButton = new Button("Отклонить");
        rejectButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 12px;");
        rejectButton.setOnAction(e -> {
            onDecision.accept(false);
            popupStage.close();
        });

        layout.getChildren().addAll(titleLabel, details, approveButton, rejectButton);

        Scene scene = new Scene(layout, 300, 200);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
