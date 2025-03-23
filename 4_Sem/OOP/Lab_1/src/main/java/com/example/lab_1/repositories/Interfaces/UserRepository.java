package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.User;
import java.util.Optional;
import java.util.List;

public interface UserRepository {
    List<User> getUsersWithoutEnterprise(String bankId);
    int getMaxUserId();
    int getUsersSalaryProject(String userId);
    void save(User user);
    boolean isUserExistByPhone(String phone);
    boolean isUserExistByPassport(String passport);
    String getUserPassword(String phone);
    Optional<User> getUserByPhoneNumber(String phone);
    Optional<User> findById(String id);
    List<User> findAll();
    List<Integer> getUserBanks(String id);
    void delete(String id);
    void assignUserToEnterprise(String userId, String enterpriseId, int amount);
    boolean deleteUserFromSalary(String id);
}
