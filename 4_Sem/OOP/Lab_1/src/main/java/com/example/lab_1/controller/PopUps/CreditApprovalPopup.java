package com.example.lab_1.controller.PopUps;

import com.example.lab_1.entities.Credit;
import com.example.lab_1.services.CreditService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class CreditApprovalPopup {
    public static void showCreditApprovalPopup(Stage parentStage, Credit creditRequest, Consumer<Boolean> onDecision) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Одобрение кредита");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10;");

        Label titleLabel = new Label("Запрос на кредит:");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label details = new Label(
                "Клиент: " + creditRequest.getUserId() + "\n" +
                        "Сумма: " + creditRequest.getAmount() + "\n" +
                        "Срок: " + creditRequest.getTerm() + " мес." + "\n" +
                        "Процент: " + creditRequest.getInterestRate() + "%");

        Button approveButton = new Button("Одобрить");
        approveButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 12px;");
        approveButton.setOnAction(e -> {
            CreditService.getInstance().approveCredit(String.valueOf(creditRequest.getId()));
            onDecision.accept(true);
            popupStage.close();
        });

        Button rejectButton = new Button("Отклонить");
        rejectButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 12px;");
        rejectButton.setOnAction(e -> {
            CreditService.getInstance().rejectCredit(String.valueOf(creditRequest.getId()));
            onDecision.accept(false);
            popupStage.close();
        });

        layout.getChildren().addAll(titleLabel, details, approveButton, rejectButton);

        Scene scene = new Scene(layout, 300, 200);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
