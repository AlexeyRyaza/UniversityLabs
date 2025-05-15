package com.app.fineapp.mapper;

import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.Category;
import com.app.fineapp.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        List<Integer> accountIds = user.getAccounts()
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList());

        List<Integer> categoryIds = user.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                accountIds,
                categoryIds
        );
    }

    public static User toEntity(UserDTO userDTO, List<Account> accounts, List<Category> categories) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAccounts(accounts);
        user.setCategories(categories);
        return user;
    }

}