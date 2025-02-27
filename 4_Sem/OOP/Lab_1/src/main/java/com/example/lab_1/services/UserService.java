package com.example.lab_1.services;

import com.example.lab_1.entities.User;
import com.example.lab_1.repositories.CouchbaseUserRepository;
import com.example.lab_1.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static UserService instance;
    private final UserRepository userRepository;

    private UserService() {
        this.userRepository = new CouchbaseUserRepository();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    //Methods
    //=======================================
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
        System.out.println("Пользователь сохранён в БД: " + user);
    }

    public User RegisterUser(String lastname, String firstname, String fathername,
                             String phone, String passport, String password, String email) {
        Optional<User> existingUser = userRepository.findById(passport);

        if (existingUser.isPresent()) {
            System.out.println("Такой пользователь уже существует!");
            return null;
        }

        User newUser = new User.Builder()
                .lastName(lastname)
                .firstName(firstname)
                .fatherName(fathername)
                .email(email)
                .passport(passport)
                .phone(phone)
                .password(password)  // В реальном приложении здесь нужно хешировать пароль!
                .build();


        saveUser(newUser);
        return newUser;
    }
}
