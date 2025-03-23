package com.example.lab_1.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicInteger;

public class Enterprise {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private final int id;
    private final String name;
    private final String unp;
    private final String address;
    private final String email;
    private final int bankId;
    private final int accountId;
    private final int salaryProjectId;
    private final boolean isVerified;

    public Enterprise() {
        this.id = -1;
        this.name = "";
        this.unp = "";
        this.address = "";
        this.email = "";
        this.bankId = -1;
        this.accountId = -1;
        this.salaryProjectId = 0;
        this.isVerified = false;
    }

    @JsonCreator
    public Enterprise(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("unp") String unp,
            @JsonProperty("address") String address,
            @JsonProperty("email") String email,
            @JsonProperty("bankId") int bankId,
            @JsonProperty("accountId") int accountId,
            @JsonProperty("salaryProjectId") int salaryProjectId,
            @JsonProperty("isVerified") boolean isVerified
    ) {
        this.id = id;
        this.name = name;
        this.unp = unp;
        this.address = address;
        this.email = email;
        this.bankId = bankId;
        this.accountId = accountId;
        this.salaryProjectId = salaryProjectId;
        this.isVerified = isVerified;
    }

    protected Enterprise(Builder builder) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.name = builder.name;
        this.unp = builder.unp;
        this.address = builder.address;
        this.email = builder.email;
        this.bankId = builder.bankId;
        this.accountId = builder.accountId;
        this.salaryProjectId = builder.salaryProjectId;
        this.isVerified = builder.isVerified;
    }

    public static class Builder {
        private String name;
        private String unp;
        private String address;
        private String email;
        private int bankId;
        private int accountId;
        private int salaryProjectId;
        private boolean isVerified;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder unp(String unp) {
            this.unp = unp;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder bankId(int bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder accountId(int accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder salaryProjectId(int salaryProjectId) {
            this.salaryProjectId = salaryProjectId;
            return this;
        }

        public Builder isVerified(boolean isVerified) {
            this.isVerified = isVerified;
            return this;
        }

        public Enterprise build() {
            return new Enterprise(this);
        }
    }

    // Getters
    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getUnp() {
        return unp;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public int getBankId() {
        return bankId;
    }

    @JsonProperty
    public int getAccountId() {
        return accountId;
    }

    @JsonProperty
    public int getsalaryProjectId() {
        return salaryProjectId;
    }

    @JsonProperty
    public boolean isVerified() {
        return isVerified;
    }

    @JsonIgnore
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации Enterprise в JSON", e);
        }
    }
}
