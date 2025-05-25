package com.app.fineapp.service;

import com.app.fineapp.dto.UserDTO;
import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.mapper.UserMapper;
import com.app.fineapp.mapper.AccountMapper;
import com.app.fineapp.mapper.CategoryMapper;
import com.app.fineapp.model.User;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.Category;
import com.app.fineapp.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final RedisService redisService;

    @Autowired
    public UserService(UserRepository userRepository,
                       AccountService accountService,
                       CategoryService categoryService,
                       RedisService redisService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.redisService = redisService;
    }

    @Async
    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#id")
    public CompletableFuture<UserDTO> getUserByIdWithCache(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        return CompletableFuture.completedFuture(UserMapper.toDTO(user));
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<UserDTO>> getAllUsers() {
        List<UserDTO> result = userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        return CompletableFuture.completedFuture(result);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<UserDTO> getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        return CompletableFuture.completedFuture(UserMapper.toDTO(user));
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<AccountDTO>> getAllAccountsByUserId(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));

        List<Account> accounts = accountService.findAllAccountsByIds(
                user.getAccounts().stream()
                        .map(Account::getId)
                        .collect(Collectors.toList())
        );
        List<AccountDTO> result = accounts.stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(result);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<CategoryDTO>> getAllCategoriesByUserId(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));

        List<Category> categories = categoryService.findAllCategoryByIds(
                user.getCategories().stream()
                        .map(Category::getId)
                        .collect(Collectors.toList())
        );

        List<CategoryDTO> result = categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(result);
    }

    @Async
    @Transactional
    public CompletableFuture<UserDTO> createUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new EntityExistsException("User already exists: " + userDTO.getEmail());
        }

        User user = UserMapper.toEntity(userDTO, new ArrayList<Account>(), new ArrayList<Category>());
        userRepository.save(user);
        return CompletableFuture.completedFuture(UserMapper.toDTO(user));
    }

    @Async
    @Transactional
    public CompletableFuture<UserDTO> updateUser(UserDTO userDTO) {
        if (!userRepository.existsById(userDTO.getId())) {
            throw new EntityNotFoundException("User not found: " + userDTO.getId());
        }
        List<Account> accounts = accountService.findAllAccountsByIds(userDTO.getAccountIds());
        List<Category> categories = categoryService.findAllCategoryByIds(userDTO.getCategoryIds());

        User user = UserMapper.toEntity(userDTO, accounts, categories);
        User saved = userRepository.save(user);
        return CompletableFuture.completedFuture(UserMapper.toDTO(saved));
    }

    @Async
    @Transactional
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Async
    @Transactional
    public CompletableFuture<Boolean> existUserByEmail(String email) {
        return CompletableFuture.completedFuture(userRepository.existsByEmail(email));
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<UserDTO> findUserByEmailWithCache(String email) {
        String key = "user:email:" + email;

        try{
            UserDTO cachedUser = redisService.get(key, UserDTO.class);
            if (cachedUser != null) {
                return CompletableFuture.completedFuture(cachedUser);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));

        UserDTO dto = UserMapper.toDTO(user);
        redisService.save(key, dto, 600);

        return CompletableFuture.completedFuture(dto);
    }

    @Async
    @Transactional
    public CompletableFuture<UserDTO> getUserByEmailAndPassword(String email, String password) {
        User user =  userRepository.findByEmailAndPassword(email, password).
                orElseThrow(() -> new EntityNotFoundException("User not found: " + email));

        return  CompletableFuture.completedFuture(UserMapper.toDTO(user));
    }


    @Transactional
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));

        return UserMapper.toDTO(user);
    }

    @Transactional
    public UserDTO findUserById(int id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("User not found: " + id));

        return UserMapper.toDTO(user);
    }
}
