package com.example.lab_1.controller;

import com.example.lab_1.entities.Credit;
import com.example.lab_1.services.CreditService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class CreditCreateController {
    @FXML
    private TextField TF_Amount;

    @FXML
    private TextField TF_InterestRate;

    @FXML
    private TextField TF_Term;

    @FXML
    private Button createButton;

    // Callback для передачи созданного кредита обратно
    private Consumer<Credit> creditCreatedCallback;

    // Данные, передаваемые из предыдущего окна
    private int userId;
    private int bankId;

    public void setUserAndBank(int userId, int bankId) {
        this.userId = userId;
        this.bankId = bankId;
    }

    public void setCreditCreatedCallback(Consumer<Credit> callback) {
        this.creditCreatedCallback = callback;
    }

    @FXML
    private void onCreateCreditClicked() {
        try {
            double amount = Double.parseDouble(TF_Amount.getText().trim());
            double interestRate = Double.parseDouble(TF_InterestRate.getText().trim());
            int term = Integer.parseInt(TF_Term.getText().trim());

            // Вычисляем ежемесячный платеж по кредиту:
            // Формула: P * r / (1 - (1 + r)^(-n))
            // где P - сумма кредита, r - месячная процентная ставка, n - срок кредита (в месяцах)
            double monthlyInterestRate = (interestRate / 100) / 12;
            double monthlyPayment = amount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -term));

            var bd = new BigDecimal(monthlyPayment).setScale(2, BigDecimal.ROUND_HALF_UP);
            monthlyPayment = bd.doubleValue();

            Credit credit = new Credit.Builder()
                    .userId(userId)
                    .bankId(bankId)
                    .amount(amount)
                    .interestRate(interestRate)
                    .term(term)
                    .monthlyPayment(monthlyPayment)
                    .build();

            CreditService.getInstance().saveCredit(credit);

            if (creditCreatedCallback != null) {
                creditCreatedCallback.accept(credit);
            }
            closeWindow();
        } catch (NumberFormatException e) {
            System.err.println("Ошибка ввода: " + e.getMessage());
            // Можно добавить уведомление пользователю, что данные введены неверно
        }
    }

    @FXML
    private void onCancelClicked() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) TF_Amount.getScene().getWindow();
        stage.close();
    }
}
