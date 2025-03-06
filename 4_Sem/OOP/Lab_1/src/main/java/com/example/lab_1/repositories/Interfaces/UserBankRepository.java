package com.example.lab_1.repositories.Interfaces;

public interface UserBankRepository {
    void saveRole(String bank_id, String user_id, String role);
    String getUserRoleByID(String user_id, String bank_id);
}
