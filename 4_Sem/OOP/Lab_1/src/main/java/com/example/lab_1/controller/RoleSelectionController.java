package com.example.lab_1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class RoleSelectionController {
    @FXML private Button clientButton;
    @FXML private Button operatorButton;
    @FXML private Button managerButton;
    @FXML private Button specialistButton;
    @FXML private Button adminButton;
    @FXML private Button confirmButton;

    private String selectedRole;
    private Consumer<String> roleCallback;
    private Button selectedButton; // Храним выбранную кнопку

    @FXML
    public void initialize() {
        clientButton.setOnAction(e -> selectRole(clientButton, "Client"));
        operatorButton.setOnAction(e -> selectRole(operatorButton, "Operator"));
        managerButton.setOnAction(e -> selectRole(managerButton, "Manager"));
        specialistButton.setOnAction(e -> selectRole(specialistButton, "Side Specialist"));
        adminButton.setOnAction(e -> selectRole(adminButton, "Administrator"));

        confirmButton.setOnAction(e -> {
            if (roleCallback != null && selectedRole != null) {
                roleCallback.accept(selectedRole);
                closeWindow();
            }
        });
    }

    public void setRoleCallback(Consumer<String> roleCallback) {
        this.roleCallback = roleCallback;
    }

    private void selectRole(Button button, String role) {
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("selected-role");
        }

        selectedRole = role;
        selectedButton = button;
        selectedButton.getStyleClass().add("selected-role");
    }

    private void closeWindow() {
        ((Stage) confirmButton.getScene().getWindow()).close();
    }
}
