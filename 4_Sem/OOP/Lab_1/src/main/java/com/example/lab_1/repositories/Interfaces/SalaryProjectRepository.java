package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.SalaryProject;
import com.example.lab_1.entities.User;

import java.util.List;
import java.util.Optional;

public interface SalaryProjectRepository {
    List<Integer> getUsersOfSalaryProject(String salaryProjectId);
    int getMaxSalaryProjectId();
    void save(SalaryProject salaryProject);
    Optional<SalaryProject> findById(String id);
    Optional<SalaryProject> findByEnterpriseId(String enterpriseId);
    List<SalaryProject> findAll();
    boolean delete(String id);
    void replace(SalaryProject salaryProject);
    List<SalaryProject> findByUserId(String userId);
    List<SalaryProject> findByBankId(String bankId);
    List<SalaryProject> findPendingApprovals(String bankId);
}
