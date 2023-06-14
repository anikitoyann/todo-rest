package com.example.todorest.dto;

import com.example.todorest.entity.Category;
import com.example.todorest.entity.Status;
import com.example.todorest.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
   private int id;
   private String title;
   private Status status;
   private User user;
   private Category category;
}
