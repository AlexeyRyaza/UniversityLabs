package com.app.fineapp.service;

import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.mapper.CategoryMapper;
import com.app.fineapp.model.Category;
import com.app.fineapp.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(categories);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<CategoryDTO> getCategoryById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));

        CategoryDTO categoryDTO = CategoryMapper.toDTO(category);
        return CompletableFuture.completedFuture(categoryDTO);
    }


    @Async
    @Transactional
    public CompletableFuture<CategoryDTO> addCategory(CategoryDTO category) {
        Category cat = CategoryMapper.toEntity(category);

        var saved = categoryRepository.save(cat);
        return CompletableFuture.completedFuture(CategoryMapper.toDTO(saved));
    }

    @Async
    @Transactional
    public CompletableFuture<CategoryDTO> updateCategory(CategoryDTO category) {
        Category cat = CategoryMapper.toEntity(category);

        if(!categoryRepository.existsById(cat.getId())) {
            throw new EntityNotFoundException("Category not found: " + cat.getId());
        }
        var saved = categoryRepository.save(cat);
        return CompletableFuture.completedFuture(CategoryMapper.toDTO(saved));
    }

    @Async
    @Transactional
    public CompletableFuture<Void> deleteCategory(int id) {
        if(!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found: " + id);
        }

        categoryRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }


    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<CategoryDTO>> getByTotalAmount(int amount) {
        List<CategoryDTO> categories = categoryRepository.findByTotalAmount(amount)
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(categories);
    }


    //===========For BackEnd uses only==============
    @Transactional(readOnly = true)
    public Category findEntityById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Category> findAllCategoryByIds(List<Integer> ids) {
        return categoryRepository.findAllById(ids);
    }
}

