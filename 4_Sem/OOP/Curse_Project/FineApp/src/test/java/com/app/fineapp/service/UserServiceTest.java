package com.app.fineapp.service;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.Category;
import com.app.fineapp.model.User;
import com.app.fineapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private AccountService accountService;
    private CategoryService categoryService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        accountService = mock(AccountService.class);
        categoryService = mock(CategoryService.class);
        userService = new UserService(userRepository, accountService, categoryService);
    }

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("Test User");

        when(userRepository.findAll()).thenReturn(List.of(user));

        CompletableFuture<List<UserDTO>> future = userService.getAllUsers();
        List<UserDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getUsername());
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("Test User");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        CompletableFuture<UserDTO> future = userService.getUserById(1);
        UserDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test User", result.getUsername());
    }

    @Test
    void getUserById_shouldThrowExceptionIfNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1).join());
    }

    @Test
    void getAllAccountsByUserId_shouldReturnAccounts() throws Exception {
        User user = new User();
        user.setId(1);
        Account account = new Account();
        account.setId(1);
        user.setAccounts(List.of(account));

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(accountService.findAllAccountsByIds(List.of(1))).thenReturn(List.of(account));

        CompletableFuture<List<AccountDTO>> future = userService.getAllAccountsByUserId(1);
        List<AccountDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void getAllAccountsByUserId_shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getAllAccountsByUserId(1).join());
    }

    @Test
    void getAllCategoriesByUserId_shouldReturnCategories() throws Exception {
        User user = new User();
        user.setId(1);
        Category category = new Category();
        category.setId(1);
        user.setCategories(List.of(category));

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(categoryService.findAllCategoryByIds(List.of(1))).thenReturn(List.of(category));

        CompletableFuture<List<CategoryDTO>> future = userService.getAllCategoriesByUserId(1);
        List<CategoryDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void getAllCategoriesByUserId_shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getAllCategoriesByUserId(1).join());
    }

    @Test
    void updateUser_shouldThrowExceptionIfUserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setUsername("Updated User");

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(userDTO).join());
    }

    @Test
    void deleteUser_shouldDeleteUserIfExists() throws Exception {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1);

        CompletableFuture<Void> future = userService.deleteUser(1);
        future.get();

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteUser_shouldThrowExceptionIfUserNotFound() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1).join());
    }
}
