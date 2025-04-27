package com.app.fineapp.model;

import com.app.fineapp.model.enums.CategoryType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category extends Icon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Version
    private Integer version;

    public Category() {
    }

    public Category(String title, BigDecimal totalAmount, CategoryType categoryType, Integer id) {
        this.id = id;
        this.title = title;
        this.totalAmount = totalAmount;
        this.categoryType = categoryType;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }
    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", totalAmount=" + totalAmount +
                ", categoryType=" + categoryType +
                ", image=" + image +
                ", color=" + color +
                ", version=" + version +
                '}';
    }
}
