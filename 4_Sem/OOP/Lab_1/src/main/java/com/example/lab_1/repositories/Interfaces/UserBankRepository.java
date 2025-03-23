package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.User;

import java.util.List;

public interface UserBankRepository {
    boolean deleteUser(String userId, String bankId);
    void saveRole(String bank_id, String user_id, String role, boolean IsApproved);
    String getUserRoleByID(String user_id, String bank_id);
    boolean IsApproved(String userId, String bankId);
    void rejectUser(String userId, String bankId);
    void approveUser(String userId, String bankId);
    List<Integer> getPendingUsersIds(String bankId);
}
