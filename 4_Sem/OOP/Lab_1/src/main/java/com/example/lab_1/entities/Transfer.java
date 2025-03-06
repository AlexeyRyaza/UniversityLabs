package com.example.lab_1.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicInteger;

public class Transfer {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private final int id;
    private final int sourceAccount;
    private final int destinationAccount;
    private final int amount;

    public Transfer() {
        this.id = -1;
        this.sourceAccount = -1;
        this.destinationAccount = -1;
        this.amount = 0;
    }

    @JsonCreator
    public Transfer(
            @JsonProperty("id") int id,
            @JsonProperty("sourceAccount") int sourceAccount,
            @JsonProperty("destinationAccount") int destinationAccount,
            @JsonProperty("amount") int amount
    ) {
        this.id = id;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    protected Transfer(Builder builder) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.sourceAccount = builder.sourceAccount;
        this.destinationAccount = builder.destinationAccount;
        this.amount = builder.amount;
    }

    public static class Builder {
        private int sourceAccount;
        private int destinationAccount;
        private int amount;

        public Builder sourceAccount(int sourceAccount) {
            this.sourceAccount = sourceAccount;
            return this;
        }

        public Builder destinationAccount(int destinationAccount) {
            this.destinationAccount = destinationAccount;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Transfer build() {
            return new Transfer(this);
        }
    }

    // Getters
    @JsonProperty
    public int getId() {
        return id;
    }

    public static void setIdGenerator(int maxId) {
        ID_GENERATOR.set(maxId);
    }


    @JsonProperty
    public int getSourceAccount() {
        return sourceAccount;
    }

    @JsonProperty
    public int getDestinationAccount() {
        return destinationAccount;
    }

    @JsonProperty
    public int getAmount() {
        return amount;
    }

    // JSON serialization
    @JsonIgnore
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Transfer to JSON", e);
        }
    }
}