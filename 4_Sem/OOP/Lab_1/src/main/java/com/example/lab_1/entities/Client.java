package com.example.lab_1.entities;

import java.util.ArrayList;
import java.util.Objects;


public class Client extends User {
    final private int bank;
    final private User user;
    private ArrayList<Integer> accounts = new ArrayList<>(); //TODO in client manager
    private SalaryProject salaryProject;

    public Client(User user, int bank) {
        this.user = user;
        this.bank = bank;
    }

    //Getters and Setters
    //=====================================================
    public int getBankID() {
        return bank;
    }

    public User getUserID() {
        return user;
    }

    public ArrayList<Integer> getAccounts() {
        return accounts;
    }

    public SalaryProject getSalaryProject() {
        return salaryProject;
    }

    public void setSalaryProject(SalaryProject salaryProject) {
        this.salaryProject = salaryProject;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(bank, client.bank) && Objects.equals(user, client.user) && Objects.equals(accounts, client.accounts) && Objects.equals(salaryProject, client.salaryProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId());
    }
}
