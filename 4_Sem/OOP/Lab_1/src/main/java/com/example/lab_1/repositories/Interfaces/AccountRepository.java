package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    int getAccountsByBankIdAndUserId(String bankId, String userId);
    void save(Account account);
    boolean delete(String id);
    void update(int sourceAccount, int destinationAccount, int amount);
    void update(int sourceAccount, int amount);
    void updateSalary(int destinationAccount, int amount);
    Optional<Account> findById(String id);
    List<Account> findAll();
    int getMaxAccountId();
    List<Account> findByUserId(String userId);
    List<Account> findByBankId(String bankId);
}
