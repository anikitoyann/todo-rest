package com.example.todorest.service.impl;

import com.example.todorest.entity.Category;
import com.example.todorest.repository.CategoryRepository;
import com.example.todorest.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id) ;
    }

    @Override
    public Category save(Category category) {
        categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean existsById(int id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Category category) {
        return categoryRepository.getCategoryById(category);
    }
}
