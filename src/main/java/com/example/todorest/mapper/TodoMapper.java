package com.example.todorest.mapper;

import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.CreateTodoResponseDto;
import com.example.todorest.dto.TodoDto;
import com.example.todorest.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")

public interface TodoMapper {
    Todo map(CreateTodoRequestDto dto);

    TodoDto mapToDto(Todo entity);

    CreateTodoResponseDto map(Todo entity);

    List<TodoDto> mapToDtoList(List<Todo> todos);
}
