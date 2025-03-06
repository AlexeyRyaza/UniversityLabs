package com.example.lab_1.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private final int accountId;

    private final int userId;
    private final int bankId;

    private double balance;
    private String currency;
    private boolean frozen;
    private boolean blocked;

    public Account() {
        this.accountId = -1;
        this.userId = -1;
        this.bankId = -1;
    }

    @JsonCreator
    public Account(
            @JsonProperty("accountId") int accountId,
            @JsonProperty("userId") int userId,
            @JsonProperty("bankId") int bankId,
            @JsonProperty("balance") double balance,
            @JsonProperty("currency") String currency,
            @JsonProperty("frozen") boolean frozen,
            @JsonProperty("blocked") boolean blocked
    ) {
        this.accountId = accountId;
        this.userId = userId;
        this.bankId = bankId;
        this.balance = balance;
        this.currency = currency;
        this.frozen = frozen;
        this.blocked = blocked;
    }

    protected Account(Builder builder) {
        this.accountId = ID_GENERATOR.incrementAndGet();
        this.userId = builder.userId;
        this.bankId = builder.bankId;
        this.balance = builder.balance;
        this.currency = builder.currency;
        this.frozen = builder.frozen;
        this.blocked = builder.blocked;
    }

    public static class Builder {
        private int userId;
        private int bankId;
        private double balance;
        private String currency = "BYN";
        private boolean frozen;
        private boolean blocked;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder bankId(int bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder balance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder frozen(boolean frozen) {
            this.frozen = frozen;
            return this;
        }

        public Builder blocked(boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    // Getters & Setters
    //========================================================
    @JsonProperty
    public int getAccountId() {
        return accountId;
    }

    @JsonProperty
    public int getUserId() {
        return userId;
    }

    @JsonProperty
    public int getBankId() {
        return bankId;
    }

    @JsonProperty
    public double getBalance() {
        return balance;
    }

    @JsonProperty
    public String getCurrency() {
        return currency;
    }

    @JsonProperty
    public boolean isFrozen() {
        return frozen;
    }

    @JsonProperty
    public boolean isBlocked() {
        return blocked;
    }

    public static void setIdGenerator(int maxId) {
        ID_GENERATOR.set(maxId);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    // JSON сериализация
    @JsonIgnore
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации Account в JSON", e);
        }
    }
}
