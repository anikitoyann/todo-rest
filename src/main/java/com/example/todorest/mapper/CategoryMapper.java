package com.example.todorest.mapper;

import com.example.todorest.dto.CategoryDto;
import com.example.todorest.dto.CreateCategoryRequestDto;
import com.example.todorest.dto.CreateCategoryResponseDto;
import com.example.todorest.entity.Category;
import org.mapstruct.Mapper;

//@Mapping(source = "name" ,target = "fullName")
@Mapper(componentModel = "spring")

public interface CategoryMapper {
    Category map(CreateCategoryRequestDto dto);

    CategoryDto mapToDto(Category entity);

    CreateCategoryResponseDto map(Category entity);

}
