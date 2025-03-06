package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.Credit;

import java.util.List;
import java.util.Optional;

public interface CreditRepository {
    void save(Credit credit);
    Optional<Credit> findById(String id);
    List<Credit> findAll();
    void delete(String id);
    List<Credit> findByUserId(String userId);
    int getMaxCreditId();
}
