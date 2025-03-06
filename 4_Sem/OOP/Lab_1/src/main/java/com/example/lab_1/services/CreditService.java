package com.example.lab_1.services;

import com.example.lab_1.entities.Credit;
import com.example.lab_1.repositories.CouchbaseCreditRepository;
import com.example.lab_1.repositories.Interfaces.CreditRepository;

import java.util.List;
import java.util.Optional;

public class CreditService {
    private static CreditService instance;
    private final CreditRepository creditRepository;

    private CreditService() {
        this.creditRepository = new CouchbaseCreditRepository();

        Credit.setIdGenerator(getMaxCreditId());
    }

    public static synchronized CreditService getInstance() {
        if (instance == null) {
            instance = new CreditService();
        }
        return instance;
    }

    public void saveCredit(Credit credit) {
        creditRepository.save(credit);
    }

    public Optional<Credit> getCreditById(String id) {
        return creditRepository.findById(id);
    }

    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }

    public List<Credit> getCreditsByUserId(String userId) {
        return creditRepository.findByUserId(userId);
    }

    public void deleteCredit(String id) {
        creditRepository.delete(id);
    }

    private int getMaxCreditId(){
        return creditRepository.getMaxCreditId();
    }
}
