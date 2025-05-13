package com.app.fineapp.controller;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public final class AccountController {
    AccountService accountService;

    @Autowired
    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public CompletableFuture<List<AccountDTO>> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    public CompletableFuture<AccountDTO> getAccountById(@PathVariable int id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/accounts")
    public CompletableFuture<AccountDTO> createAccount(@RequestBody AccountDTO account) {
        return accountService.createAccount(account);
    }

    @PutMapping("/accounts")
    public CompletableFuture<AccountDTO> updateAccount(@RequestBody AccountDTO account) {
        return accountService.updateAccount(account);
    }

    @DeleteMapping("/accounts/{id}")
    public CompletableFuture<Void> deleteAccount(@PathVariable int id) {
        return accountService.deleteAccount(id);
    }
}
