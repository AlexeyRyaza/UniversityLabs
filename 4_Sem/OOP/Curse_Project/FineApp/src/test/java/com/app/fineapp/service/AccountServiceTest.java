package com.app.fineapp.service;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.OperationDTO;
import com.app.fineapp.mapper.AccountMapper;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.Operation;
import com.app.fineapp.model.enums.AccountType;
import com.app.fineapp.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void getAllAccounts_shouldReturnList() throws Exception {
        Account account = new Account();
        account.setId(1);
        account.setTitle("Test Account");
        account.setBalance(BigDecimal.valueOf(100));
        when(accountRepository.findAll()).thenReturn(List.of(account));

        CompletableFuture<List<AccountDTO>> future = accountService.getAllAccounts();
        List<AccountDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals("Test Account", result.get(0).getTitle());
    }

    @Test
    void getAccountById_shouldReturnAccount() throws Exception {
        Account account = new Account();
        account.setId(1);
        account.setTitle("Test Account");
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        CompletableFuture<AccountDTO> future = accountService.getAccountById(1);
        AccountDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void getAccountById_shouldThrowExceptionIfNotFound() {
        when(accountRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountById(1).join());
    }

    @Test
    void createAccount_shouldSaveAccount() throws Exception {
        AccountDTO dto = new AccountDTO();
        dto.setTitle("New");
        dto.setBalance(BigDecimal.valueOf(300));
        dto.setType(AccountType.Regular);
        dto.setImage(0);
        dto.setColor(0);

        Account saved = AccountMapper.toEntity(dto);
        saved.setId(1);

        when(accountRepository.save(any(Account.class))).thenReturn(saved);

        CompletableFuture<AccountDTO> future = accountService.createAccount(dto);
        AccountDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New", result.getTitle());
    }

    @Test
    void updateAccount_shouldUpdateIfExists() throws Exception {
        AccountDTO dto = new AccountDTO();
        dto.setId(1);
        dto.setTitle("Updated");
        dto.setImage(0);
        dto.setColor(0);

        when(accountRepository.existsById(1)).thenReturn(true);
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> {
            Account acc = i.getArgument(0);
            acc.setId(1);
            return acc;
        });

        CompletableFuture<AccountDTO> future = accountService.updateAccount(dto);
        AccountDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Updated", result.getTitle());
    }

    @Test
    void updateAccount_shouldThrowIfNotExists() {
        AccountDTO dto = new AccountDTO();
        dto.setId(1);
        dto.setTitle("Test Name"); // Заполним еще поля на всякий случай
        dto.setColor(0);
        dto.setImage(0);

        // когда будет вызван existsById с id из DTO, вернуть false
        when(accountRepository.existsById(dto.getId())).thenReturn(false);

        // Проверка что будет выброшено исключение
        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> accountService.updateAccount(dto).join()
        );

        assertEquals("Account not found: 1", thrown.getMessage());
    }


    @Test
    void deleteAccount_shouldDeleteIfExists() throws Exception {
        when(accountRepository.existsById(1)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(1);

        CompletableFuture<Void> future = accountService.deleteAccount(1);
        future.get();

        verify(accountRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteAccount_shouldThrowIfNotExists() {
        when(accountRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> accountService.deleteAccount(1).join());
    }

    @Test
    void findByType_shouldReturnAccounts() throws Exception {
        Account account = new Account();
        account.setId(2);
        account.setTitle("Cash Account");
        account.setType(AccountType.Regular);

        when(accountRepository.findByType(AccountType.Regular)).thenReturn(List.of(account));

        CompletableFuture<List<AccountDTO>> future = accountService.findByType(AccountType.Regular);
        List<AccountDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals(AccountType.Regular, result.get(0).getType());
    }

    @Test
    void getOperationsForAccount_shouldReturnOperations() throws Exception {
        Account account = new Account();
        account.setId(1);

        Operation op = new Operation();
        op.setId(1);
        op.setAmount(BigDecimal.valueOf(100));
        op.setComment("Test Operation");
        op.setDate(LocalDateTime.now());

        account.setOperations(List.of(op));

        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        AccountDTO dto = new AccountDTO();
        dto.setId(1);

        CompletableFuture<List<OperationDTO>> future = accountService.getOperationsForAccount(dto);
        List<OperationDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(100), result.get(0).getAmount());
    }

    @Test
    void findEntityById_shouldReturnEntity() {
        Account account = new Account();
        account.setId(1);

        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        Account result = accountService.findEntityById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void findEntityById_shouldThrowIfNotFound() {
        when(accountRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.findEntityById(1));
    }

    @Test
    void findAllAccountsByIds_shouldReturnAccounts() {
        Account account = new Account();
        account.setId(1);

        when(accountRepository.findAllById(List.of(1))).thenReturn(List.of(account));

        List<Account> result = accountService.findAllAccountsByIds(List.of(1));

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }
}
