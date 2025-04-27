package com.app.fineapp.controller;

import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.model.Category;
import com.app.fineapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public final class CategoryController {
    CategoryService categoryService;

    @Autowired
    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public CompletableFuture<List<CategoryDTO>> getOperations() {
        return categoryService.getAllCategories();
    }
    @GetMapping("/categories/{id}")
    public CompletableFuture<CategoryDTO> getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/categories")
    public CompletableFuture<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/categories")
    public CompletableFuture<CategoryDTO> updateCategory(@RequestBody CategoryDTO category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public CompletableFuture<Void> deleteCategory(@PathVariable int id) {
        return categoryService.deleteCategory(id);
    }
}
