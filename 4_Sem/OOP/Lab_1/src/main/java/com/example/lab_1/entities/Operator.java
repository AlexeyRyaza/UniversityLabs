package com.example.lab_1.entities;

public class Operator {
    final private Bank bank;
    final private User user;

    public Operator(Bank bank, User user) {
        this.bank = bank;
        this.user = user;

        bank.addOperator(this);
    }

    //Getters
    public User getUser() {
        return user;
    }

    public Bank getBank() {
        return bank;
    }
}
