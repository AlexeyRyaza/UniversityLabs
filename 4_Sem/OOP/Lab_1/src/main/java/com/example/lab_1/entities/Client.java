package com.example.lab_1.entities;

import java.util.ArrayList;
import java.util.Objects;


public class Client {
    final private Bank bank;
    final private User user;
    private ArrayList<Account> accounts = new ArrayList<>(); //TODO in client manager
    private SalaryProject salaryProject;

    public Client(User user, Bank bank) {
        this.user = user;
        this.bank = bank;

        bank.addClient(this);// TODO request which can be approved by manager
    }

    //Getters and Setters
    //=====================================================
    public Bank getBank() {
        return bank;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Account> getAccounts() {
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
