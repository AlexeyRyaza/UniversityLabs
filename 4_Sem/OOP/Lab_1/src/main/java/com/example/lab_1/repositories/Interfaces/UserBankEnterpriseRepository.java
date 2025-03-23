package com.example.lab_1.repositories.Interfaces;

public interface UserBankEnterpriseRepository {
    void saveInfo(String userId, String bankId, String enterpriseId);
    int getEnterpriseByUserAndBankId(String userId, String bankId);
}
