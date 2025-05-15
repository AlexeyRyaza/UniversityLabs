package com.app.fineapp.service;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.OperationDTO;
import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.mapper.AccountMapper;
import com.app.fineapp.mapper.OperationMapper;
import com.app.fineapp.mapper.UserMapper;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.Category;
import com.app.fineapp.model.Operation;
import com.app.fineapp.model.User;
import com.app.fineapp.repository.OperationRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final AccountService accountService;
    private final CategoryService categoryService;


    public OperationService(OperationRepository operationRepository, AccountService accountService, CategoryService categoryService) {
        this.operationRepository = operationRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<OperationDTO> getOperationById(int id) {
        var operation = operationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation not found: " + id));

        OperationDTO operationDTO = OperationMapper.toDTO(operation);

        return CompletableFuture.completedFuture(operationDTO);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<OperationDTO>> getAllOperations() {
        List<OperationDTO> operations = operationRepository.findAll()
                .stream()
                .map(OperationMapper::toDTO)
                .collect(Collectors.toList());

        if (operations.isEmpty()) {
            throw new EntityNotFoundException("Operation not found");
        }

        return CompletableFuture.completedFuture(operations);
    }

    @Async
    @Transactional
    public CompletableFuture<OperationDTO> createOperation(OperationDTO operationDTO) {
        if(operationRepository.existsById(operationDTO.getId())) {
            throw new EntityExistsException("Operation already exists: " + operationDTO.getId());
        }

        Account account = accountService.findEntityById((operationDTO.getAccountId()));
        Category category = categoryService.findEntityById((operationDTO.getCategoryId()));

        Operation operation = OperationMapper.toEntity(operationDTO, account, category);
        var saved = operationRepository.save(operation);

        return CompletableFuture.completedFuture(OperationMapper.toDTO(saved));
    }

    @Async
    @Transactional
    public CompletableFuture<OperationDTO> updateOperation(OperationDTO operationDTO) {
        Operation existingOperation = operationRepository.findById(operationDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Operation not found: " + operationDTO.getId()));

        existingOperation.setAmount(operationDTO.getAmount());
        existingOperation.setComment(operationDTO.getComment());
        existingOperation.setDate(operationDTO.getDate());
        existingOperation.setAccount(accountService.findEntityById(operationDTO.getAccountId()));
        existingOperation.setCategory(categoryService.findEntityById(operationDTO.getCategoryId()));

        var saved = operationRepository.save(existingOperation);
        return CompletableFuture.completedFuture(OperationMapper.toDTO(saved));
    }

    @Async
    @Transactional
    public CompletableFuture<Void> deleteOperationById(int id) {
        if(!operationRepository.existsById(id)) {
            throw new EntityNotFoundException("Operation not found: " + id);
        }

        operationRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<OperationDTO>> getOperationsByAccount(AccountDTO account) {
        if(accountService.findEntityById(account.getId()) == null) {
            throw new EntityNotFoundException("Account not found: " + account.getId());
        }

        List<OperationDTO> ops = operationRepository.findByAccount(AccountMapper.toEntity(account))
                .stream()
                .map(OperationMapper::toDTO)
                .collect(Collectors.toList());

        if(ops.isEmpty()) {
            throw new EntityNotFoundException("Operation not found, Account id: " + account.getId());
        }

        return CompletableFuture.completedFuture(ops);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<OperationDTO>> getMonthlyOperations(AccountDTO account) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.withDayOfMonth(1).toLocalDate().atStartOfDay();

        if(accountService.findEntityById(account.getId()) == null) {
            throw new EntityNotFoundException("Account not found: " + account.getId());
        }

        List<OperationDTO> ops = operationRepository.findByAccountAndDateBetween(AccountMapper.toEntity(account), start, now)
                .stream()
                .map(OperationMapper::toDTO)
                .collect(Collectors.toList());

        if(ops.isEmpty()) {
            throw new EntityNotFoundException("Operation not found, Account id: " + account.getId());
        }

        return CompletableFuture.completedFuture(ops);
    }


    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<OperationDTO>> getOperationsForUser(UserDTO userDTO) {
        List<Account> accounts = accountService.findAllAccountsByIds(userDTO.getAccountIds());

        if(accounts.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        List<OperationDTO> ops = operationRepository.findByAccountIn(accounts)
                .stream()
                .map(OperationMapper::toDTO)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(ops);
    }
}