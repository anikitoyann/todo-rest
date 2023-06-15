package com.example.todorest.mapper;

import com.example.todorest.dto.CreateUserRequestDto;
import com.example.todorest.dto.UserDto;
import com.example.todorest.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(CreateUserRequestDto dto);

    UserDto mapToDto(User entity);

}