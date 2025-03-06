package com.example.lab_1.services;

import com.example.lab_1.entities.Bank;
import com.example.lab_1.repositories.CouchbaseBankRepository;

import java.util.List;
import java.util.Optional;

public class BankService {
    private static BankService instance;
    private final CouchbaseBankRepository bankRepository;

    private BankService() {
        this.bankRepository = new CouchbaseBankRepository();
    }

    public static synchronized BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
        }
        return instance;
    }

    public void saveBank(Bank bank) {
        bankRepository.save(bank);
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Optional<Bank> getBankById(String id) {
        return bankRepository.findById(id);
    }

    public void deleteBank(String id) {
        bankRepository.delete(id);
    }
}