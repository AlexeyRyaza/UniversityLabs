package com.example.lab_1.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    final private Stage primaryStage;

    public SceneManager(Stage stage) {
        this.primaryStage = stage;
    }

    public void showInitialScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/InitialPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/LoginPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRegistrationScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/RegistrationPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFinishRegistrationScene(String passport, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/FinishRegistrationPage.fxml"));
            Parent root = loader.load();

            FinishRegistrationController controller = loader.getController();
            controller.initData(passport, password); // Передаем данные

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBankSelectionScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/BankSelectionPage.fxml"));
            Parent root = loader.load();

            BankSelectionController controller = loader.getController();
            controller.showBanks();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
