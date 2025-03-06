package com.example.lab_1.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicInteger;

public class Credit {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private boolean isApproved;
    private final int id;
    private final int userId;
    private final int bankId;
    private final double amount;
    private final double interestRate;
    private final int term;
    private final double monthlyPayment;

    public Credit() {
        this.id = -1;
        this.userId = -1;
        this.bankId = -1;
        this.amount = 0;
        this.interestRate = 0;
        this.term = 0;
        this.monthlyPayment = 0;
        this.isApproved = false;
    }

    @JsonCreator
    public Credit(
            @JsonProperty("id") int id,
            @JsonProperty("userId") int userId,
            @JsonProperty("bankId") int bankId,
            @JsonProperty("amount") double amount,
            @JsonProperty("interestRate") double interestRate,
            @JsonProperty("term") int term,
            @JsonProperty("monthlyPayment") double monthlyPayment,
            @JsonProperty("isApproved") boolean isApproved
    ) {
        this.id = id;
        this.userId = userId;
        this.bankId = bankId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.isApproved = isApproved;
    }

    protected Credit(Builder builder) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.userId = builder.userId;
        this.bankId = builder.bankId;
        this.amount = builder.amount;
        this.interestRate = builder.interestRate;
        this.term = builder.term;
        this.monthlyPayment = builder.monthlyPayment;
        this.isApproved = builder.isApproved;
    }

    public static class Builder {
        private int userId;
        private int bankId;
        private double amount;
        private double interestRate;
        private int term;
        private double monthlyPayment;
        private boolean isApproved;

        public Builder isApproved(boolean isApproved) {
            this.isApproved = isApproved;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder bankId(int bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder interestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public Builder term(int term) {
            this.term = term;
            return this;
        }

        public Builder monthlyPayment(double monthlyPayment) {
            this.monthlyPayment = monthlyPayment;
            return this;
        }

        public Credit build() {
            return new Credit(this);
        }
    }

    // Getters
    @JsonProperty
    public boolean isApproved() {
        return isApproved;
    }

    public static void setIdGenerator(int maxId) {
        ID_GENERATOR.set(maxId);
    }

    @JsonProperty
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @JsonProperty
    public int getId() {
        return id;
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
    public double getAmount() {
        return amount;
    }

    @JsonProperty
    public double getInterestRate() {
        return interestRate;
    }

    @JsonProperty
    public int getTerm() {
        return term;
    }

    @JsonProperty
    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    @JsonIgnore
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Credit to JSON", e);
        }
    }
}
