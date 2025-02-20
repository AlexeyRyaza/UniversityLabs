package com.example.lab_1.entities;

public class Specialist {
    final private Bank bank;
    final private User user;

    public Specialist(Bank bank, User user) {
        this.bank = bank;
        this.user = user;

        bank.addSpecialist(this);
    }

    //Getters
    public User getUser() {
        return user;
    }

    public Bank getBank() {
        return bank;
    }
}
