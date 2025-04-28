package com.app.fineapp.service;

import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.mapper.CategoryMapper;
import com.app.fineapp.model.Category;
import com.app.fineapp.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void getAllCategories_shouldReturnList() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setTitle("Test Category");

        when(categoryRepository.findAll()).thenReturn(List.of(category));

        CompletableFuture<List<CategoryDTO>> future = categoryService.getAllCategories();
        List<CategoryDTO> result = future.get();

        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getTitle());
    }

    @Test
    void getCategoryById_shouldReturnCategory() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setTitle("Test Category");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        CompletableFuture<CategoryDTO> future = categoryService.getCategoryById(1);
        CategoryDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Category", result.getTitle());
    }

    @Test
    void getCategoryById_shouldThrowExceptionIfNotFound() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getCategoryById(1).join());
    }

    @Test
    void createCategory_shouldSaveCategory() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        dto.setTitle("New Category");
        dto.setColor(0);
        dto.setImage(0);

        Category saved = CategoryMapper.toEntity(dto);
        saved.setId(1);

        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        CompletableFuture<CategoryDTO> future = categoryService.createCategory(dto);
        CategoryDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New Category", result.getTitle());
    }

    @Test
    void updateCategory_shouldUpdateIfExists() throws Exception {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1);
        dto.setTitle("Updated Category");
        dto.setColor(0);
        dto.setImage(0);

        when(categoryRepository.existsById(1)).thenReturn(true);
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> {
            Category category = i.getArgument(0);
            category.setId(1);
            return category;
        });

        CompletableFuture<CategoryDTO> future = categoryService.updateCategory(dto);
        CategoryDTO result = future.get();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Updated Category", result.getTitle());
    }

    @Test
    void updateCategory_shouldThrowIfNotExists() {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1);
        dto.setTitle("Test Category");
        dto.setColor(0);
        dto.setImage(0);

        when(categoryRepository.existsById(dto.getId())).thenReturn(false);

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.updateCategory(dto).join()
        );

        assertEquals("Category not found: 1", thrown.getMessage());
    }

    @Test
    void deleteCategory_shouldDeleteIfExists() throws Exception {
        when(categoryRepository.existsById(1)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1);

        CompletableFuture<Void> future = categoryService.deleteCategory(1);
        future.get();

        verify(categoryRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteCategory_shouldThrowIfNotExists() {
        when(categoryRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(1).join());
    }
}
