package com.app.fineapp.controller;

import com.app.fineapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final private UserService userService;

    @Autowired
    AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register/{email}")
    public CompletableFuture<Boolean> verifyEmail(@PathVariable String email) {
        return userService.existUserByEmail(email);
    }

}

