package com.example.lab_1.controller;

import com.example.lab_1.controller.Autentification.FinishRegistrationController;
import com.example.lab_1.controller.Roles.*;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Authentication/InitialPage.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Authentication/LoginPage.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Authentication/RegistrationPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFinishRegistrationScene(String passport, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Authentication/FinishRegistrationPage.fxml"));
            Parent root = loader.load();

            FinishRegistrationController controller = loader.getController();
            controller.initData(passport, password); // Передаем данные

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBankSelectionScene(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/BankSelectionPage.fxml"));
            Parent root = loader.load();

            BankSelectionController controller = loader.getController();
            controller.initData(userId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showClientScene(int userId, int bankId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Roles/ClientPage.fxml"));
            Parent root = loader.load();

            ClientPageController controller = loader.getController();
            controller.initData(userId, bankId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSideSpecialistScene(int userId, int bankId, int enterpriseId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Roles/SpecialistPage.fxml"));
            Parent root = loader.load();

            SideSpecialistPageController controller = loader.getController();
            controller.initData(userId, bankId, enterpriseId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showOperatorScene(int userId, int bankId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Roles/OperatorPage.fxml"));
            Parent root = loader.load();

            OperatorPageController controller = loader.getController();
            controller.initData(userId, bankId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showManagerScene(int userId, int bankId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Roles/ManagerPage.fxml"));
            Parent root = loader.load();

            ManagerPageController controller = loader.getController();
            controller.initData(userId, bankId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAdminScene(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_1/Roles/AdminPage.fxml"));
            Parent root = loader.load();

            AdminPageController controller = loader.getController();
            controller.initData(userId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
