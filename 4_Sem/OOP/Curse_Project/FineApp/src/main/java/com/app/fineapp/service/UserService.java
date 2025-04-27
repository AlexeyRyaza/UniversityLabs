package com.app.fineapp.service;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.mapper.AccountMapper;
import com.app.fineapp.mapper.CategoryMapper;
import com.app.fineapp.mapper.UserMapper;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.Category;
import com.app.fineapp.model.User;
import com.app.fineapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserRepository userRepository;

    AccountService accountService;
    CategoryService categoryService;

    @Autowired
    UserService(UserRepository userRepository, AccountService accountService, CategoryService categoryService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(users);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<UserDTO> getUserById(int id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("User not found: " + id));

        return CompletableFuture.completedFuture(UserMapper.toDTO(user));
    }

    @Async
    @Transactional
    public CompletableFuture<List<AccountDTO>> getAllAccountsByUserId(UserDTO userDTO) {
        List<Account> accounts = accountService.findAllAccountsByIds(userDTO.getAccountIds());
        List<AccountDTO> accountDTOS = accounts.stream()
                .map(AccountMapper::toDTO)
                .toList();

        return CompletableFuture.completedFuture(accountDTOS);
    }

    @Async
    @Transactional
    public CompletableFuture<List<CategoryDTO>> getAllCategoriesByUserId(UserDTO userDTO) {
        List<Category> categories = categoryService.findAllCategoryByIds(userDTO.getCategoryIds());
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(CategoryMapper::toDTO)
                .toList();

        return CompletableFuture.completedFuture(categoryDTOS);
    }

    @Async
    @Transactional
    public CompletableFuture<UserDTO> createUser(UserDTO userDTO) {
        List<Account> accounts = accountService.findAllAccountsByIds(userDTO.getAccountIds());
        List<Category> categories = categoryService.findAllCategoryByIds(userDTO.getCategoryIds());

        User user = UserMapper.toEntity(userDTO, accounts, categories);
        userRepository.save(user);

        return CompletableFuture.completedFuture(UserMapper.toDTO(user));
    }

    @Async
    @Transactional
    public CompletableFuture<UserDTO> updateUser(UserDTO userDTO) {
        List<Account> accounts = accountService.findAllAccountsByIds(userDTO.getAccountIds());
        List<Category> categories = categoryService.findAllCategoryByIds(userDTO.getCategoryIds());

        User user = UserMapper.toEntity(userDTO, accounts, categories);

        if(!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("User not found: " + user.getId());
        }
        var saved = userRepository.save(user);
        return CompletableFuture.completedFuture(UserMapper.toDTO(saved));
    }

    @Async
    @Transactional
    public CompletableFuture<Void> deleteUser(int id) {
        if(!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }

        userRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }


}
