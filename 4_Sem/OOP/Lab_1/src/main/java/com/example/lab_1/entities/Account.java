package com.example.lab_1.entities;

import com.example.lab_1.entities.Enums.Currency;
import com.example.lab_1.entities.Enums.Status;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    final private int id;
    final private long balance;
    final private Client owner;
    final private Currency currency;

    private Stack<Long> history = new Stack<Long>(); // TODO Long -> Operation
    private Status status;

    //history impl
    //=============================================
    public boolean addHistoryLog(Long change){
        return history.add(change);
    }

    //Builder
    //==============================================
    private Account(Builder builder) {
        id = ID_GENERATOR.getAndIncrement();
        this.balance = builder.balance;
        this.owner = builder.owner;
        this.currency = builder.currency;
        this.status = builder.status;
    }

    public static class Builder {
        private long balance;
        private Client owner;
        private Currency currency;
        private Status status;

        public Builder owner(Client owner) {
            this.owner = owner;
            return this;
        }
        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }
        public Builder status(Status status) {
            this.status = status;
            return this;
        }
        public Builder balance(long balance) {
            this.balance = balance;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    //Factory
    //===============================================
    public interface AccountFactory{
        Account createAccount(Client owner, Currency currency, Status status);
    }

    public static class RegularAccountFactory implements AccountFactory {
        @Override
        public Account createAccount(Client owner, Currency currency, Status status) {
            return new Account.Builder()
                    .owner(owner)
                    .currency(currency)
                    .status(status)
                    .balance(1000)  // Example default value for regular account
                    .build();
        }
    }

    public static class SalaryAccountFactory implements AccountFactory {
        @Override
        public Account createAccount(Client owner, Currency currency, Status status) {
            return new Account.Builder()
                    .owner(owner)
                    .currency(currency)
                    .status(status)
                    .balance(0)
                    .build();
        }
    }

    public static class CreditAccountFactory implements AccountFactory {
        @Override
        public Account createAccount(Client owner, Currency currency, Status status) {
            return new Account.Builder()
                    .owner(owner)
                    .currency(currency)
                    .status(status)
                    .balance(-500)  // Example default value for credit account
                    .build();
        }
    }


    //Getters and Setters
    public Stack<Long> getHistory() {
        return history;
    }

    public int getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public Client getOwner() {
        return owner;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
