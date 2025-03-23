package com.example.lab_1.services;

import com.example.lab_1.entities.Enterprise;
import com.example.lab_1.repositories.CouchbaseEnterpriseRepository;
import com.example.lab_1.repositories.Interfaces.EnterpriseRepository;

import java.util.List;
import java.util.Optional;

public class EnterpriseService {
    private static EnterpriseService instance;
    private final EnterpriseRepository enterpriseRepository;

    private EnterpriseService() {
        this.enterpriseRepository = new CouchbaseEnterpriseRepository();
    }

    public static synchronized EnterpriseService getInstance() {
        if (instance == null) {
            instance = new EnterpriseService();
        }
        return instance;
    }

    public Optional<Enterprise> getEnterpriseByName(String name) {
        return enterpriseRepository.getEnterpriseByName(name);
    }

    public void saveEnterprise(Enterprise enterprise) {
        enterpriseRepository.save(enterprise);
    }

    public Optional<Enterprise> getEnterpriseById(String id) {
        return enterpriseRepository.findById(id);
    }

    public List<Enterprise> getAllEnterprises() {
        return enterpriseRepository.findAll();
    }

    public List<Enterprise> getEnterprisesByBankId(String bankId) {
        return enterpriseRepository.findByBankId(bankId);
    }

    public List<Enterprise> getUnverifiedEnterprises() {
        return enterpriseRepository.findUnverifiedEnterprises();
    }

    public void deleteEnterprise(String id) {
        enterpriseRepository.delete(id);
    }

    public void verifyEnterprise(String id) {
        Optional<Enterprise> enterpriseOpt = enterpriseRepository.findById(id);
        enterpriseOpt.ifPresent(enterprise -> {
            Enterprise verifiedEnterprise = new Enterprise.Builder()
                    .name(enterprise.getName())
                    .unp(enterprise.getUnp())
                    .address(enterprise.getAddress())
                    .email(enterprise.getEmail())
                    .bankId(enterprise.getBankId())
                    .salaryProjectId(enterprise.getsalaryProjectId())
                    .isVerified(true) // Подтверждаем предприятие
                    .build();
            enterpriseRepository.save(verifiedEnterprise);
        });
    }
}
