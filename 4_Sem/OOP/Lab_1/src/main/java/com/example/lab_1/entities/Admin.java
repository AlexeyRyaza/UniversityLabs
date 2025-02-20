package com.example.lab_1.entities;

public class Admin {
    final private Bank bank;
    final private User user;

    public Admin(Bank bank, User user) {
        this.bank = bank;
        this.user = user;

        bank.addAdmin(this);
    }

    //Getters
    public User getUser() {
        return user;
    }

    public Bank getBank() {
        return bank;
    }
}
