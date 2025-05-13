package com.app.fineapp.dto;

import com.app.fineapp.model.enums.CategoryType;
import java.math.BigDecimal;

public class CategoryDTO {
    private Integer id;
    private BigDecimal totalAmount;
    private String title;
    private CategoryType type;

    private Integer image;
    private Integer color;

    // геттеры/сеттеры
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public CategoryType getType() { return type; }
    public void setType(CategoryType type) { this.type = type; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Integer getImage() { return image; }
    public void setImage(Integer image) { this.image = image; }

    public Integer getColor() { return color; }
    public void setColor(Integer color) { this.color = color; }
}
