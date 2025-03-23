package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.Credit;

import java.util.List;
import java.util.Optional;

public interface CreditRepository {
    void save(Credit credit);
    Optional<Credit> findById(String id);
    List<Credit> findAll();
    List<Credit> findByBankId(String bankId);
    void approveCredit(String id);
    boolean delete(String id);
    List<Credit> findByUserId(String userId);
    int getMaxCreditId();
}
