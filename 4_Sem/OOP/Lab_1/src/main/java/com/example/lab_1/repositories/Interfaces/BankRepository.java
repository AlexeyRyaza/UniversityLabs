package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository {
    void save(Bank bank);
    void delete(String id);
    Optional<Bank> findById(String id);
    List<Bank> findAll();
}
