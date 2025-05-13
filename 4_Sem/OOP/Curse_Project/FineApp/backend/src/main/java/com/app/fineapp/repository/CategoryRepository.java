package com.app.fineapp.repository;

import com.app.fineapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByTotalAmount(int totalAmount); // твой кастомный метод
}
