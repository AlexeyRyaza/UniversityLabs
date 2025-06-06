package com.app.fineapp.controller;

import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/categories")
public final class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public CompletableFuture<List<CategoryDTO>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CompletableFuture<CategoryDTO> getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public CompletableFuture<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        return categoryService.createCategory(category);
    }

    @PutMapping
    public CompletableFuture<CategoryDTO> updateCategory(@RequestBody CategoryDTO category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteCategory(@PathVariable int id) {
        return categoryService.deleteCategory(id);
    }
}
