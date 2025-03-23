package com.example.lab_1.services;

import com.example.lab_1.repositories.CouchBaseUserBankEnterpriseRepository;
import com.example.lab_1.repositories.CouchbaseUserBankRepository;
import com.example.lab_1.repositories.Interfaces.UserBankEnterpriseRepository;

public class UserBankEnterpriseService {
    private static UserBankEnterpriseService instance;
    private final CouchBaseUserBankEnterpriseRepository userBankEnterpriseRepository;

    private UserBankEnterpriseService() {
        this.userBankEnterpriseRepository = new CouchBaseUserBankEnterpriseRepository();
    }

    public static synchronized UserBankEnterpriseService getInstance() {
        if (instance == null) {
            instance = new UserBankEnterpriseService();
        }
        return instance;
    }

    public int getEnterpriseByUserAndBankID(String userId, String bankId) {
        return userBankEnterpriseRepository.getEnterpriseByUserAndBankId(userId, bankId);
    }

    public void saveInfo(String userId, String bankId, String enterpriseId) {
        userBankEnterpriseRepository.saveInfo(userId, bankId, enterpriseId);
    }
}