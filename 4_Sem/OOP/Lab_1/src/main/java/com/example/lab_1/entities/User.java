package com.example.lab_1.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lab_1.entities.Enums.Role;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Map;

public class User {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private final String lastName;
    private final String firstName;
    private final String fatherName;
    private final String email;
    private final String phone;
    private final String passport;
    private String password;
    private final int id;

    private Map<String, Role> bankRoles = new HashMap<>();

    public User() {
        this.lastName = "";
        this.firstName = "";
        this.fatherName = "";
        this.email = "";
        this.phone = "";
        this.passport = "";
        this.password = "";
        this.id = -1;
        this.bankRoles = new HashMap<>();
    }

    @JsonCreator
    public User(
            @JsonProperty("lastName") String lastName,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("fatherName") String fatherName,
            @JsonProperty("email") String email,
            @JsonProperty("phone") String phone,
            @JsonProperty("passport") String passport,
            @JsonProperty("password") String password,
            @JsonProperty("id") int id,
            @JsonProperty("bankRoles") Map<String, Role> bankRoles
    ) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.email = email;
        this.phone = phone;
        this.passport = passport;
        this.password = password;
        this.id = id;
        this.bankRoles = bankRoles != null ? bankRoles : new HashMap<>();
    }

    public void addBank(String bankId) {
        bankRoles.putIfAbsent(bankId, null);
    }

    public void registerInBank(String bankId, Role role) {
        bankRoles.put(bankId, role);
    }

    public Role getRoleInBank(String bankId) {
        return bankRoles.get(bankId);
    }

    //Builder realization
    //=========================================
    protected User(Builder<?> builder) {
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.fatherName = builder.fatherName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.passport = builder.passport;
        this.password = builder.password;
        this.id = ID_GENERATOR.incrementAndGet();
    }

    public static class Builder<T extends Builder<T>> {
        private String lastName;
        private String firstName;
        private String fatherName;
        private String email;
        private String phone;
        private String passport;
        private String password;


        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder fatherName(String fatherName) {
            this.fatherName = fatherName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder passport(String passport) {
            this.passport = passport;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }


        public User build(){
            return new User(this);
        }
    }
    //=================================================

    //Getters and Setters
    //==================================================
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public String getFatherName() {
        return fatherName;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public String getPhone() {
        return phone;
    }

    @JsonProperty
    public String getPassport() {
        return passport;
    }

    @JsonProperty
    public int getId() {
        return id;
    }


    // Метод для автоматического логина во всех банках, где роль задана //TODO Auto-login in existing accounts
    public void loginToBanks() {

    }

    @JsonIgnore
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации User в JSON", e);
        }
    }
}
