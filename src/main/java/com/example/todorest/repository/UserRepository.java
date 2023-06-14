package com.example.todorest.repository;

import com.example.todorest.entity.User;
import com.example.todorest.security.CurrentUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    User getUserByEmail(String username);
}