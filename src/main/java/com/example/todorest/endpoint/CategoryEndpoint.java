package com.example.todorest.endpoint;
import com.example.todorest.dto.CategoryDto;
import com.example.todorest.dto.CreateCategoryRequestDto;
import com.example.todorest.dto.CreateCategoryResponseDto;
import com.example.todorest.entity.Category;
import com.example.todorest.mapper.CategoryMapper;
import com.example.todorest.repository.CategoryRepository;
import com.example.todorest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryEndpoint {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<CreateCategoryResponseDto> create(@RequestBody CreateCategoryRequestDto requestDto) {
        Optional<Category> byId = categoryRepository.findById(requestDto.getId());
        if (byId.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Category category = categoryMapper.map(requestDto);
        categoryRepository.save(category);
        return ResponseEntity.ok(categoryMapper.map(category));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<Category> all = categoryRepository.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<CategoryDto>categoryDtos=new ArrayList<>();
        for (Category category : all) {
            categoryDtos.add(categoryMapper.mapToDto(category));
        }
        return ResponseEntity.ok(categoryDtos);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") int id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") int id, @RequestBody Category category) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Category categoryFromDB = byId.get();
        if (category.getName() != null && !category.getName().isEmpty()) {
            categoryFromDB.setName(category.getName());
        }

        Category updatedCategory = categoryRepository.save(categoryFromDB);
        return ResponseEntity.ok(updatedCategory);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
