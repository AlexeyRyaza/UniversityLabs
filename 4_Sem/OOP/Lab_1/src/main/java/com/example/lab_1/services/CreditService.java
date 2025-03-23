package com.example.lab_1.services;

import com.example.lab_1.entities.Credit;
import com.example.lab_1.entities.LogEntry;
import com.example.lab_1.repositories.CouchbaseCreditRepository;
import com.example.lab_1.repositories.Interfaces.CreditRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public boolean deleteCredit(String id) {
        return creditRepository.delete(id);
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

    public List<Credit> getPendingCredits(String bankId) {
        return creditRepository.findByBankId(bankId)
                .stream()
                .filter(credit -> !credit.isApproved())
                .collect(Collectors.toList());
    }

    public void approveCredit(String id) {
        creditRepository.approveCredit(id);

        LogService.logAction(new LogEntry(
                "credit_approval",
                id,
                "Кредит " + id + " был одобрен"
        ));
    }
    public void rejectCredit(String id) {
        creditRepository.delete(id);
    }

    private int getMaxCreditId(){
        return creditRepository.getMaxCreditId();
    }
}
