package com.example.todorest.endpoint;

import com.example.todorest.dto.*;
import com.example.todorest.entity.Type;
import com.example.todorest.entity.User;
import com.example.todorest.mapper.UserMapper;
import com.example.todorest.repository.UserRepository;
import com.example.todorest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private final UserMapper userMapper;

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userRepository.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(userAuthRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token, user.getName(), user.getSurname()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto){
        Optional<User> byEmail = userRepository.findByEmail(createUserRequestDto.getEmail());
        if(byEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        user.setType(Type.USER);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.mapToDto(user));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDto userDto = userMapper.mapToDto(user);
        userDto.setPassword(null);
        return ResponseEntity.ok(userDto);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody User user) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User userFromDB = byId.get();
        if (user.getName() != null && !user.getName().isEmpty()) {
            user.setName(user.getName());
        }
        if (user.getSurname() != null && !user.getSurname().isEmpty()) {
            user.setSurname(user.getSurname());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            user.setEmail(user.getEmail());
        }

        User updatedUser = userRepository.save(userFromDB);
        return ResponseEntity.ok(updatedUser);
    }


}