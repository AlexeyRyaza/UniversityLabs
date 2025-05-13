package com.app.fineapp.controller;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализируем моки
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllAccounts() throws Exception {
        // Подготовка тестовых данных
        AccountDTO accountDTO1 = new AccountDTO();
        accountDTO1.setId(1);
        accountDTO1.setTitle("Account 1");

        AccountDTO accountDTO2 = new AccountDTO();
        accountDTO2.setId(2);
        accountDTO2.setTitle("Account 2");

        when(accountService.getAllAccounts()).thenReturn(CompletableFuture.completedFuture(Arrays.asList(accountDTO1, accountDTO2)));

        // Выполнение GET запроса и проверка результатов
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    void testGetAccountById() throws Exception {
        // Подготовка тестовых данных
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setTitle("Account 1");

        when(accountService.getAccountById(1)).thenReturn(CompletableFuture.completedFuture(accountDTO));

        // Выполнение GET запроса и проверка результатов
        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getAccountById(1);
    }

    @Test
    void testCreateAccount() throws Exception {
        // Подготовка тестовых данных
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setTitle("New Account");

        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(CompletableFuture.completedFuture(accountDTO));

        mockMvc.perform(post("/accounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isOk());

        verify(accountService, times(1)).createAccount(any(AccountDTO.class));
    }

    @Test
    void testUpdateAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setTitle("Updated Account");

        when(accountService.updateAccount(any(AccountDTO.class))).thenReturn(CompletableFuture.completedFuture(accountDTO));

        mockMvc.perform(put("/accounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isOk());

        verify(accountService, times(1)).updateAccount(any(AccountDTO.class));
    }

    @Test
    void testDeleteAccount() throws Exception {
        // Подготовка тестовых данных
        when(accountService.deleteAccount(1)).thenReturn(CompletableFuture.completedFuture(null));

        // Выполнение DELETE запроса и проверка результатов
        mockMvc.perform(delete("/accounts/1"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).deleteAccount(1);
    }
}
