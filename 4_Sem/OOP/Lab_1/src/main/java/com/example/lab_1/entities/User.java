package com.example.lab_1.entities;

import com.example.lab_1.entities.Enums.Role;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Map;

public class User {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private String lastName;
    private String firstName;
    private String fatherName;
    private String email;
    private String phone;
    private String passport;
    private String password;

    private final int id;

    private Map<Bank, Role> bankRoles = new HashMap<>();

    public void addBank(Bank bank) {
        bankRoles.putIfAbsent(bank, null);
    }

    public void registerInBank(Bank bank, Role role) {
        bankRoles.put(bank, role);
    }

    public Role getRoleInBank(Bank bank) {
        return bankRoles.get(bank);
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


        public T lastName(String lastName) {
            this.lastName = lastName;
            return self();
        }

        public T firstName(String firstName) {
            this.firstName = firstName;
            return self();
        }

        public T fatherName(String fatherName) {
            this.fatherName = fatherName;
            return self();
        }

        public T email(String email) {
            this.email = email;
            return self();
        }

        public T phone(String phone) {
            this.phone = phone;
            return self();
        }

        public T passport(String passport) {
            this.passport = passport;
            return self();
        }

        public T password(String password) {
            this.password = password;
            return self();
        }

        protected abstract T self();

        public abstract User build();
    }
    //=================================================

    //Getters and Setters
    //==================================================
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getId() {
        return id;
    }


    // Метод для автоматического логина во всех банках, где роль задана //TODO Auto-login in existing accounts
    public void loginToBanks() {
        for (Map.Entry<Bank, Role> entry : bankRoles.entrySet()) {
            Bank bank = entry.getKey();
            Role role = entry.getValue();
            if (role != null) {
                // Здесь можно вызвать метод, который выполнит процесс логина.
                // Например, bank.loginUser(this, role);

            }
        }
    }
}
