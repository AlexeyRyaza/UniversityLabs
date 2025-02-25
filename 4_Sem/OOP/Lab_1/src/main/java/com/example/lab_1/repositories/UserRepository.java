package com.example.lab_1.repositories;

import com.example.lab_1.entities.User;
import java.util.Optional;
import java.util.List;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(String id);

    List<User> findAll();

    void delete(String id);
}
