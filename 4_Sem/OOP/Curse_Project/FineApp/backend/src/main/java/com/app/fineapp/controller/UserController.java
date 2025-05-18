package com.app.fineapp.controller;

import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public CompletableFuture<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public CompletableFuture<UserDTO> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/get/{email}")
    public CompletableFuture<UserDTO> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{id}/accounts")
    public CompletableFuture<List<AccountDTO>> getAllAccountsByUserId(@PathVariable int id) {
        return userService.getAllAccountsByUserId(id);
    }

    @GetMapping("/{id}/categories")
    public CompletableFuture<List<CategoryDTO>> getAllCategoriesByUserId(@PathVariable int id) {
        return userService.getAllCategoriesByUserId(id);
    }

    @PostMapping("/")
    public CompletableFuture<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public CompletableFuture<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
