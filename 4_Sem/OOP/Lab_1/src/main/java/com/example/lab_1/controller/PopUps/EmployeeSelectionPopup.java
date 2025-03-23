package com.example.lab_1.controller.PopUps;

import com.example.lab_1.entities.User;
import com.example.lab_1.services.UserService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class EmployeeSelectionPopup {
    public static void showEmployeeSelectionPopup(Stage parentStage, int bankId, Consumer<User> onUserSelected) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setTitle("Выберите сотрудников");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10;");

        Label titleLabel = new Label("Доступные пользователи:");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 5 0;");

        List<User> availableUsers = UserService.getInstance().getUsersWithoutEnterprise(String.valueOf(bankId));

        VBox userList = new VBox(10);
        userList.setPadding(new Insets(5));

        if (availableUsers.isEmpty()) {
            Label emptyLabel = new Label("Нет доступных пользователей.");
            emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            userList.getChildren().add(emptyLabel);
        } else {
            for (User user : availableUsers) {
                HBox userRow = new HBox(10);
                userRow.setPadding(new Insets(5));
                userRow.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 8;");

                Label userInfo = new Label(user.getFirstName() + " " + user.getLastName());
                userInfo.setStyle("-fx-font-size: 14px;");

                Button addButton = new Button("Добавить");
                addButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 5;");
                addButton.setOnAction(e -> {
                    onUserSelected.accept(user);
                    addButton.setDisable(true); // Отключаем кнопку после добавления
                });

                userRow.getChildren().addAll(userInfo, addButton);
                userList.getChildren().add(userRow);
            }
        }

        layout.getChildren().addAll(titleLabel, userList);

        Scene scene = new Scene(layout, 350, 450);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
