package com.example.lab_1.entities;

public class Manager {
    final private Bank bank;
    final private User user;

    public Manager(Bank bank, User user) {
        this.bank = bank;
        this.user = user;

        bank.addManager(this);
    }

    //Getters
    public User getUser() {
        return user;
    }

    public Bank getBank() {
        return bank;
    }
}
