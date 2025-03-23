package com.example.lab_1.services;

import com.example.lab_1.entities.LogEntry;
import com.example.lab_1.services.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.lab_1.services.LogService.updateLogFile;

public class RollBackService {
    private static RollBackService instance;

    private RollBackService() {}

    public static synchronized RollBackService getInstance() {
        if (instance == null) {
            instance = new RollBackService();
        }
        return instance;
    }

    public boolean rollbackLastAction() {
        List<LogEntry> logs = LogService.getLogs();
        if (logs.isEmpty()) {
            System.out.println("Нет логов для отката.");
            return false;
        }

        LogEntry lastLog = logs.get(logs.size() - 1); // Берем последний лог
        return rollbackAction(lastLog);
    }

    public boolean rollbackAction(LogEntry log) {
        boolean success = false;

        String[] ids = log.getTargetIds().split(",");
        switch (log.getActionType()) {
            case "user_registration":
                success = rollbackUserRegistration(ids);
                break;
            case "credit_approval":
                success = rollbackCreditApproval(ids);
                break;
            case "salary_project_creation":
                success = rollbackSalaryProject(ids);
                break;
            default:
                System.out.println("Неизвестное действие: " + log.getActionType());
                return false;
        }

        if (success) {
            markLogAsReversed(log);
        }
        return success;
    }

    private void markLogAsReversed(LogEntry log) {
        log.markReversed();
        updateLogFile(log);
    }

    private boolean rollbackUserRegistration(String[] ids) {
        if (ids.length < 2) return false;

        String userId = ids[0];
        String bankId = ids[1];

        System.out.println("Откат регистрации пользователя " + userId + " в банке " + bankId);
        return UserBankService.getInstance().deleteUser(userId, bankId);
    }

    private boolean rollbackCreditApproval(String[] ids) {
        if (ids.length < 1) return false;

        String creditId = ids[0];

        System.out.println("Откат кредита с ID: " + creditId);
        return CreditService.getInstance().deleteCredit(creditId);
    }

    private boolean rollbackSalaryProject(String[] ids) {
        if (ids.length < 1) return false;

        String projectId = ids[0];

        System.out.println("Откат зарплатного проекта с ID: " + projectId);
        return SalaryProjectService.getInstance().deleteSalaryProject(projectId);
    }
}
