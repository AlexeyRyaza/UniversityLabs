package com.example.lab_1.services;

import com.example.lab_1.entities.Transfer;
import com.example.lab_1.repositories.CouchbaseTransferRepository;

import java.util.List;
import java.util.Optional;

public class TransferService {
    private static TransferService instance;
    private final CouchbaseTransferRepository transferRepository;

    private TransferService() {
        this.transferRepository = new CouchbaseTransferRepository();

        Transfer.setIdGenerator(getMaxTransferId());
    }

    public static synchronized TransferService getInstance() {
        if (instance == null) {
            instance = new TransferService();
        }
        return instance;
    }

    public List<Transfer> getTransfersByBank(String bankId) {
        return transferRepository.findByBank(bankId);
    }

    public List<Transfer> getTransfersByBankAndEnterprise(String bankId) {
        return transferRepository.findByBankAndEnterprise(bankId);
    }

    public void createTransfer(int sourceAccount, int destinationAccount, int amount) {
        AccountService.getInstance().updateAccount(sourceAccount, destinationAccount, amount);

        TransferService.getInstance().saveTransfer(new Transfer.Builder()
                .amount(amount)
                .destinationAccount(destinationAccount)
                .sourceAccount(sourceAccount)
                .build());
    }

    public void createTransfer(int sourceAccount, int amount) {
        AccountService.getInstance().updateAccount(sourceAccount, amount);

        TransferService.getInstance().saveTransfer(new Transfer.Builder()
                .amount(amount)
                .destinationAccount(-1)
                .sourceAccount(sourceAccount)
                .build());
    }
    public void createSalaryTransfer(int destinationAccount, int amount, int sourceEnterprise) {
        AccountService.getInstance().updateSalaryAccount(destinationAccount, amount);

        TransferService.getInstance().saveTransfer(new Transfer.Builder()
                .amount(amount)
                .destinationAccount(destinationAccount)
                .sourceAccount(-sourceEnterprise)
                .build());
    }

    public void saveTransfer(Transfer transfer) {
        transferRepository.save(transfer);
    }

    public Optional<Transfer> getTransferById(String id) {
        return transferRepository.findById(id);
    }

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public int getMaxTransferId(){ return transferRepository.getMaxTransferId();}

    public List<Transfer> findByDestinationAccount(String destinationAccount) {
        return transferRepository.findByDestinationAccount(destinationAccount);
    }
    public List<Transfer> findBySourceAccount(String sourceAccount) {
        return transferRepository.findBySourceAccount(sourceAccount);
    }
}