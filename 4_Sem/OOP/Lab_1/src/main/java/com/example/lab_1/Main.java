package com.example.lab_1;

import com.example.lab_1.controller.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws IOException {
        sceneManager = new SceneManager(stage);
        sceneManager.showLoginScene();
        stage.show();
    }

    public static SceneManager getInstance() {
        return sceneManager;
    }

    public static void main(String[] args) {
        launch();
    }
}