package com.example.todorest.repository;
import com.example.todorest.entity.Status;
import com.example.todorest.entity.Todo;
import com.example.todorest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    Todo createTodo(Todo todo);

    User getUserByEmail(String username);

    Todo updateStatus(Todo todo);

    List<Todo> getTodosByStatusAndUser(Status status, User user);
}