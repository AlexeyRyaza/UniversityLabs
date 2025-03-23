package com.example.lab_1;

/*
    Approve of client
    Approve of credit +
    Approve of Salary project +
 */

//MyCluster
//Administrator
//123456

import com.example.lab_1.infrastructure.CouchbaseConnection;
import com.example.lab_1.controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws IOException {
        sceneManager = new SceneManager(stage);
        sceneManager.showInitialScene();
        stage.show();
    }

    public static SceneManager getInstance() {
        return sceneManager;
    }

    public static void main(String[] args) {
        CouchbaseConnection.initialize();

        Runtime.getRuntime().addShutdownHook(new Thread(CouchbaseConnection::disconnect));

        launch();
    }
}