package com.example.lab_1.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicInteger;

public class Bank {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private final int id;
    private final String name;
    private final String UNP;
    private final String address;

    public Bank() {
        this.id = -1;
        this.name = "";
        this.UNP = "";
        this.address = "";
    }

    @JsonCreator
    public Bank(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("UNP") String UNP,
            @JsonProperty("address") String address
    ) {
        this.id = id;
        this.name = name;
        this.UNP = UNP;
        this.address = address;
    }

    public static void setIdGenerator(int maxId) {
        ID_GENERATOR.set(maxId);
    }

    // Builder
    // ========================================
    protected Bank(Builder builder) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.name = builder.name;
        this.UNP = builder.UNP;
        this.address = builder.address;
    }

    public static class Builder {
        private String name;
        private String UNP;
        private String address;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder UNP(String UNP) {
            this.UNP = UNP;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Bank build() {
            return new Bank(this);
        }
    }

    // Getters
    // ========================================
    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getUNP() {
        return UNP;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonIgnore
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации Bank в JSON", e);
        }
    }
}