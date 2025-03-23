package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.Enterprise;
import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository {
    Optional<Enterprise> getEnterpriseByName(String name);
    void save(Enterprise enterprise);
    Optional<Enterprise> findById(String id);
    List<Enterprise> findAll();
    void delete(String id);
    List<Enterprise> findByBankId(String bankId);
    List<Enterprise> findUnverifiedEnterprises();
}
