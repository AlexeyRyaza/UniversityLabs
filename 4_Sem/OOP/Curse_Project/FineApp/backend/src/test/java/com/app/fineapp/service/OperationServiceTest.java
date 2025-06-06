package com.app.fineapp.service;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.OperationDTO;
import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.Category;
import com.app.fineapp.model.Operation;
import com.app.fineapp.model.User;
import com.app.fineapp.repository.OperationRepository;
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

class OperationServiceTest {

    private OperationRepository operationRepository;
    private AccountService accountService;
    private CategoryService categoryService;
    private OperationService operationService;

    @BeforeEach
    void setUp() {
        operationRepository = mock(OperationRepository.class);
        accountService = mock(AccountService.class);
        categoryService = mock(CategoryService.class);
        operationService = new OperationService(operationRepository, accountService, categoryService);
    }

    @Test
    void getOperationById_shouldReturnOperation() throws Exception {
        Operation operation = new Operation();
        operation.setId(1);
        operation.setAmount(BigDecimal.valueOf(100));
        operation.setComment("Test Operation");
        operation.setDate(LocalDateTime.now());

        when(operationRepository.findById(1)).thenReturn(Optional.of(operation));

        CompletableFuture<OperationDTO> future = operationService.getOperationById(1);
        OperationDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(BigDecimal.valueOf(100), result.getAmount());
    }

    @Test
    void getOperationById_shouldThrowExceptionIfNotFound() {
        when(operationRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> operationService.getOperationById(1).join());
    }

    @Test
    void getAllOperations_shouldReturnList() throws Exception {
        Operation operation = new Operation();
        operation.setId(1);
        operation.setAmount(BigDecimal.valueOf(100));
        operation.setComment("Test Operation");
        operation.setDate(LocalDateTime.now());

        when(operationRepository.findAll()).thenReturn(List.of(operation));

        CompletableFuture<List<OperationDTO>> future = operationService.getAllOperations();
        List<OperationDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals("Test Operation", result.get(0).getComment());
    }

    @Test
    void createOperation_shouldSaveOperation() throws Exception {
        OperationDTO dto = new OperationDTO();
        dto.setAmount(BigDecimal.valueOf(200));
        dto.setComment("New Operation");
        dto.setAccountId(1);
        dto.setCategoryId(1);
        dto.setImage(0);
        dto.setColor(0);

        Account account = new Account();
        Category category = new Category();

        Operation saved = new Operation();
        saved.setId(1);
        saved.setAmount(BigDecimal.valueOf(200));
        saved.setComment("New Operation");

        when(accountService.findEntityById(1)).thenReturn(account);
        when(categoryService.findEntityById(1)).thenReturn(category);
        when(operationRepository.save(any(Operation.class))).thenReturn(saved);

        CompletableFuture<OperationDTO> future = operationService.createOperation(dto);
        OperationDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New Operation", result.getComment());
    }

    @Test
    void updateOperation_shouldUpdateIfExists() throws Exception {
        OperationDTO dto = new OperationDTO();
        dto.setId(1);
        dto.setAmount(BigDecimal.valueOf(150));
        dto.setComment("Updated Operation");
        dto.setAccountId(1);
        dto.setCategoryId(1);

        Account account = new Account();
        Category category = new Category();

        Operation existingOperation = new Operation();
        existingOperation.setId(1);
        existingOperation.setAmount(BigDecimal.valueOf(100));
        existingOperation.setComment("Old Operation");

        when(operationRepository.findById(1)).thenReturn(Optional.of(existingOperation));
        when(accountService.findEntityById(1)).thenReturn(account);
        when(categoryService.findEntityById(1)).thenReturn(category);
        when(operationRepository.save(any(Operation.class))).thenReturn(existingOperation);

        CompletableFuture<OperationDTO> future = operationService.updateOperation(dto);
        OperationDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(BigDecimal.valueOf(150), result.getAmount());
        assertEquals("Updated Operation", result.getComment());
    }

    @Test
    void updateOperation_shouldThrowIfNotExists() {
        OperationDTO dto = new OperationDTO();
        dto.setId(1);
        dto.setAmount(BigDecimal.valueOf(150));
        dto.setComment("Updated Operation");

        when(operationRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> operationService.updateOperation(dto).join());
    }

    @Test
    void deleteOperationById_shouldDeleteIfExists() throws Exception {
        when(operationRepository.existsById(1)).thenReturn(true);
        doNothing().when(operationRepository).deleteById(1);

        CompletableFuture<Void> future = operationService.deleteOperationById(1);
        future.get();

        verify(operationRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteOperationById_shouldThrowIfNotExists() {
        when(operationRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> operationService.deleteOperationById(1).join());
    }




    @Test
    void getOperationsForUser_shouldReturnOperationsForUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setAccountIds(List.of(1));
        userDTO.setCategoryIds(List.of(1));

        Account account = new Account();
        Category category = new Category();
        User user = new User();
        user.setAccounts(List.of(account));

        Operation operation = new Operation();
        operation.setId(1);
        operation.setAmount(BigDecimal.valueOf(100));

        when(accountService.findAllAccountsByIds(userDTO.getAccountIds())).thenReturn(List.of(account));
        when(categoryService.findAllCategoryByIds(userDTO.getCategoryIds())).thenReturn(List.of(category));
        when(operationRepository.findByAccountIn(anyList())).thenReturn(List.of(operation));

        CompletableFuture<List<OperationDTO>> future = operationService.getOperationsForUser(userDTO);
        List<OperationDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(100), result.get(0).getAmount());
    }
}
