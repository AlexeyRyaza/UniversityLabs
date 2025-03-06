package com.example.lab_1.services;

import com.example.lab_1.entities.User;
import com.example.lab_1.repositories.CouchbaseUserRepository;
import com.example.lab_1.repositories.Interfaces.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {
    private static UserService instance;
    private final UserRepository userRepository;

    private UserService() {
        this.userRepository = new CouchbaseUserRepository();

        User.setIdGenerator(userRepository.getMaxUserId());
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    //Methods
    //=======================================
    public boolean isUserExistByPhone(String phone) {
        return userRepository.isUserExistByPhone(phone);
    }

    public boolean isUserExistByPassport(String passport) {
        return userRepository.isUserExistByPassport(passport);
    }

    public Optional<User> getUserByPhoneNumber(String Phone) {
        return userRepository.getUserByPhoneNumber(Phone);
    }

    public String getUserPassword(String phone) {
        return userRepository.getUserPassword(phone);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<Integer> getUserBanks(String id) {
        return userRepository.getUserBanks(id);
    }
}
