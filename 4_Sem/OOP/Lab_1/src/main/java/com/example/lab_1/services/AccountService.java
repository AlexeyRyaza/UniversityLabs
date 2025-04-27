package com.example.lab_1.services;

import com.example.lab_1.entities.Account;
import com.example.lab_1.repositories.CouchbaseAccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountService {
    private static AccountService instance;
    private final CouchbaseAccountRepository accountRepository;

    private AccountService() {
        this.accountRepository = new CouchbaseAccountRepository();

        Account.setIdGenerator(accountRepository.getMaxAccountId());
    }

    public static synchronized AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public void updateAccount(int sourceAccount, int destinationAccount, int amount) {
        accountRepository.update(sourceAccount, destinationAccount, amount);
    }

    public void updateAccount(int sourceAccount, int amount) {
        accountRepository.update(sourceAccount, amount);
    }

    public void updateSalaryAccount(int destinationAccount, int amount) {
        accountRepository.updateSalary(destinationAccount, amount);
    }


    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public boolean deleteAccount(String id) {
        return accountRepository.delete(id);
    }

    public Optional<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByUserId(String userId) {
        return accountRepository.findByUserId(userId);
    }

    public List<Account> getAccountsByBankId(String userId, String bankId) {
        return accountRepository.findByBankId(userId, bankId);
    }

    public int getAccountByBankIdAndUserId(String bankId, String userId) {
        return accountRepository.getAccountsByBankIdAndUserId(bankId, userId);
    }

    public void blockAccount(String id, boolean state) {
        Account account;
        if(getAccountById(id).isPresent())
            account = getAccountById(id).get();
        else return;

        account.setBlocked(state);
        accountRepository.replace(account);
    }

    public void freezeAccount(String id) {
        Account account;
        if(getAccountById(id).isPresent())
            account = getAccountById(id).get();
        else return;

        account.setFrozen(!account.isFrozen());
        accountRepository.replace(account);
    }
}
