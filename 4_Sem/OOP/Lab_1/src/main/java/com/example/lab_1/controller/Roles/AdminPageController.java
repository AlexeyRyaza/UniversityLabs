package com.example.lab_1.controller.Roles;

import com.example.lab_1.Main;
import com.example.lab_1.entities.LogEntry;
import com.example.lab_1.services.LogService;
import com.example.lab_1.services.RollBackService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class AdminPageController {
    @FXML private VBox VB_Logs;

    private int userId;

    public void initData(int userId) {
        this.userId = userId;

        loadLogs();
    }

    @FXML
    protected void onBackButtonClicked() {
        Main.getInstance().showBankSelectionScene(userId);
    }

    @FXML
    protected void onRollbackLastAction() {
        boolean success = RollBackService.getInstance().rollbackLastAction();
        if (success) {
            loadLogs();
        }
    }

    public void loadLogs() {
        VB_Logs.getChildren().clear();
        List<LogEntry> logs = LogService.getLogs();

        for (LogEntry log : logs) {
            HBox logItem = createLogItem(log);
            VB_Logs.getChildren().add(logItem);
        }
    }

    private HBox createLogItem(LogEntry log) {
        HBox logItem = new HBox(15);
        logItem.getStyleClass().add("log-item");

        Label logInfo = new Label(
                "[" + log.getTimestamp() + "] " + log.getActionType() + " (ID: " + log.getTargetIds() + ")"
        );
        logInfo.getStyleClass().add("log-info");

        Button rollbackButton = new Button("Откатить");
        rollbackButton.getStyleClass().add("rollback-button");
        rollbackButton.setOnAction(event -> rollbackAction(log));

        logItem.getChildren().addAll(logInfo, rollbackButton);
        return logItem;
    }

    private void rollbackAction(LogEntry log) {
        boolean success = RollBackService.getInstance().rollbackAction(log);
        if (success) {
            loadLogs(); //TODO Изменять файл Логов после ужаления лога
        }
    }
}
