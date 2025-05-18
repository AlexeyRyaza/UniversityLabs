package com.app.fineapp.controller;

import com.app.fineapp.dto.AuthRequest;
import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.security.jwt.JwtService;
import com.app.fineapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> login(@RequestBody AuthRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            UserDTO user = userService.findUserByEmail(request.getEmail());
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!user.getPassword().equals(request.getPassword())){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            String email = request.getEmail();
            String jwt = jwtService.generateToken(email);


            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "user", user
            ));
        });
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> register(@RequestBody UserDTO userDTO) {
        return userService.existUserByEmail(userDTO.getEmail())
                .thenCompose(exists -> {
                    if (exists) {
                        return CompletableFuture.completedFuture(
                                ResponseEntity.badRequest().body(Map.of("error", "User already exists"))
                        );
                    }

                    return userService.createUser(userDTO)
                            .thenApply(user -> {
                                String jwt = jwtService.generateToken(user.getEmail());
                                return ResponseEntity.ok(Map.of(
                                        "token", jwt,
                                        "user", user
                                ));
                            });
                });
    }


    @GetMapping("/check_email/{email}")
    public CompletableFuture<Boolean> checkEmail(@PathVariable String email) {
        return userService.existUserByEmail(email);
    }
}

