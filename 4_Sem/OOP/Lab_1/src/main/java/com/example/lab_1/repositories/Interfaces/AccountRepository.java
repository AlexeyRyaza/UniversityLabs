package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    void delete(String id);
    void update(int sourceAccount, int destinationAccount, int amount);
    void update(int sourceAccount, int amount);
    Optional<Account> findById(String id);
    List<Account> findAll();
    int getMaxAccountId();
    List<Account> findByUserId(String userId);
    List<Account> findByBankId(String bankId);
}
