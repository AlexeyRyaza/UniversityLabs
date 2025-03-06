package com.example.lab_1.repositories.Interfaces;

import com.example.lab_1.entities.Transfer;
import com.example.lab_1.services.TransferService;

import java.util.List;
import java.util.Optional;

public interface TransferRepository {
    void save(Transfer transfer);
    void delete(String id);
    Optional<Transfer> findById(String id);
    List<Transfer> findAll();
    int getMaxTransferId();
    List<Transfer> findByDestinationAccount(String destinationAccount);
    List<Transfer> findBySourceAccount(String sourceAccount);
}
