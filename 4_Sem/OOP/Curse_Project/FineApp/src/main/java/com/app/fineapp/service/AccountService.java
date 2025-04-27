package com.app.fineapp.service;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.OperationDTO;
import com.app.fineapp.mapper.AccountMapper;
import com.app.fineapp.mapper.OperationMapper;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.enums.AccountType;
import com.app.fineapp.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountRepository.findAll()
                .stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
        return  CompletableFuture.completedFuture(accounts);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<AccountDTO> getAccountById(int id) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));

        AccountDTO accountDTO = AccountMapper.toDTO(acc);
        return CompletableFuture.completedFuture(accountDTO);
    }

    @Async
    @Transactional
    public CompletableFuture<AccountDTO> createAccount(AccountDTO accountDTO) {
        Account acc = AccountMapper.toEntity(accountDTO);
        Account saved = accountRepository.save(acc);

        return CompletableFuture.completedFuture(AccountMapper.toDTO(saved));
    }

    @Async
    @Transactional
    public CompletableFuture<AccountDTO> updateAccount(AccountDTO accountDTO) {
        Account acc = AccountMapper.toEntity(accountDTO);

        if (!accountRepository.existsById(acc.getId())) {
            throw new EntityNotFoundException("Account not found: " + acc.getId());
        }
        Account saved = accountRepository.save(acc);
        return CompletableFuture.completedFuture(AccountMapper.toDTO(saved));
    }

    @Async
    @Transactional
    public CompletableFuture<Void> deleteAccount(int id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found: " + id);
        }
        accountRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<AccountDTO>> findByType(AccountType type) {
        List<Account> account = accountRepository.findByType(type);
        List<AccountDTO> accountsDTO = account
                .stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(accountsDTO);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<OperationDTO>> getOperationsForAccount(AccountDTO accountDTO) {
        Account account = findEntityById(accountDTO.getId());

        List<OperationDTO> ops = account.getOperations()
                .stream()
                .map(OperationMapper::toDTO)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(ops);
    }

    //===========For BackEnd uses only==============
    @Transactional(readOnly = true)
    public Account findEntityById(int id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Account> findAllAccountsByIds(List<Integer> ids) {
        return accountRepository.findAllById(ids);
    }
}
