package com.app.fineapp.controller;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public CompletableFuture<List<UserDTO>> getAllUsers() {
        return  userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public CompletableFuture<UserDTO> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("users/{id}/accounts")
    public CompletableFuture<List<AccountDTO>> getAllAccountsByUserId(@PathVariable int id) {
        return userService.getAllAccountsByUserId(id);
    }

    @GetMapping("users/{id}/categories")
    public CompletableFuture<List<CategoryDTO>> getAllCategoriesByUserId(@PathVariable int id) {
        return userService.getAllCategoriesByUserId(id);
    }

    @PostMapping("/users")
    public CompletableFuture<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/users")
    public CompletableFuture<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public CompletableFuture<Void> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}
