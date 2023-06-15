package com.example.todorest.service;

import com.example.todorest.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(int id);

    Category save(Category category);

    List<Category> findAll();

    boolean existsById(int id);

    void deleteById(int id);

    Category getCategoryById(Category category);
}
