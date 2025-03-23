package com.example.lab_1.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicInteger;

public class SalaryProject {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private final int id;
    private final int enterpriseId;
    private final int bankId;
    private boolean approved;

    public SalaryProject() {
        this.id = -1;
        this.enterpriseId = -1;
        this.bankId = -1;
        this.approved = false;
    }

    @JsonCreator
    public SalaryProject(
            @JsonProperty("id") int id,
            @JsonProperty("enterpriseId") int enterpriseId,
            @JsonProperty("bankId") int bankId,
            @JsonProperty("approved") boolean approved
    ) {
        this.id = id;
        this.enterpriseId = enterpriseId;
        this.bankId = bankId;
        this.approved = approved;
    }

    protected SalaryProject(Builder builder) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.enterpriseId = builder.enterpriseId;
        this.bankId = builder.bankId;
        this.approved = builder.approved;
    }

    public static class Builder {
        private int enterpriseId;
        private int bankId;
        private boolean approved;

        public Builder enterpriseId(int enterpriseId) {
            this.enterpriseId = enterpriseId;
            return this;
        }

        public Builder bankId(int bankId) {
            this.bankId = bankId;
            return this;
        }

        public Builder approved(boolean approved) {
            this.approved = approved;
            return this;
        }

        public SalaryProject build() {
            return new SalaryProject(this);
        }
    }

    public static void setIdGenerator(int maxId) {
        ID_GENERATOR.set(maxId);
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public int getEnterpriseId() {
        return enterpriseId;
    }

    @JsonProperty
    public int getBankId() {
        return bankId;
    }

    @JsonProperty
    public boolean approved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @JsonIgnore
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации SalaryProject в JSON", e);
        }
    }
}
