package com.example.todorest.endpoint;
import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.TodoDto;
import com.example.todorest.entity.Category;
import com.example.todorest.entity.Status;
import com.example.todorest.entity.Todo;
import com.example.todorest.entity.User;
import com.example.todorest.mapper.TodoMapper;
import com.example.todorest.repository.CategoryRepository;
import com.example.todorest.repository.TodoRepository;
import com.example.todorest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/todo")
@RestController
public class TodoEndpoint {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoMapper todoMapper;
    private final CategoryRepository categoryRepository;

    @PostMapping()
    public ResponseEntity<TodoDto> createTodo(@RequestBody CreateTodoRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.getUserByEmail(userDetails.getUsername());
        Category category = categoryRepository.getCategoryById(requestDto.getCategory());
        Todo todo = new Todo();
        todo.setTitle(requestDto.getTitle());
        todo.setStatus(Status.NOT_STARTED);
        todo.setUser(user);
        todo.setCategory(category);
        Todo createdTodo = todoRepository.createTodo(todo);
        TodoDto todoDto = todoMapper.mapToDto(createdTodo);

        return ResponseEntity.status(HttpStatus.CREATED).body(todoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodoStatus(@PathVariable("id") int id, @RequestParam("status") Status status, @AuthenticationPrincipal UserDetails userDetails) {
        Todo todo = todoRepository.getById(id);
        User user = userRepository.getUserByEmail(userDetails.getUsername());

        if (todo == null || !todo.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }

        todo.setStatus(status);
        Todo updatedTodo = todoRepository.updateStatus(todo);
        TodoDto todoDTO = todoMapper.mapToDto(updatedTodo);

        return ResponseEntity.ok(todoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") int id, @AuthenticationPrincipal UserDetails userDetails) {
        Todo todo = todoRepository.getById(id);
        User user = userRepository.getUserByEmail(userDetails.getUsername());

        if (todo == null || !todo.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }

        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable("id") int id, @AuthenticationPrincipal UserDetails userDetails) {
        Todo todo = todoRepository.getById(id);
        User user = todoRepository.getUserByEmail(userDetails.getUsername());

        if (todo == null || !todo.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }

        TodoDto todoDTO = todoMapper.mapToDto(todo);
        return ResponseEntity.ok(todoDTO);
    }
    @GetMapping("/byStatus")
    public ResponseEntity<List<TodoDto>> getTodosByStatus(@RequestParam("status") Status status, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.getUserByEmail(userDetails.getUsername());
        List<Todo> todos = todoRepository.getTodosByStatusAndUser(status, user);

        if (todos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<TodoDto> todoDtos = todoMapper.mapToDtoList(todos);
        return ResponseEntity.ok(todoDtos);
    }
}
