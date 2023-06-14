package com.example.todorest.repository;

import com.example.todorest.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(int id);

    Category getCategoryById(Category category);
}