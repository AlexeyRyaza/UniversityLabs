package com.app.fineapp.mapper;

import com.app.fineapp.dto.CategoryDTO;
import com.app.fineapp.model.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setTotalAmount(category.getTotalAmount());
        dto.setTitle(category.getTitle());
        dto.setType(category.getCategoryType());
        dto.setImage(category.getImage());
        dto.setColor(category.getColor());

        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setTotalAmount(dto.getTotalAmount());
        category.setTitle(dto.getTitle());
        category.setCategoryType(dto.getType());
        category.setImage(dto.getImage());
        category.setColor(dto.getColor());

        return category;
    }
}
