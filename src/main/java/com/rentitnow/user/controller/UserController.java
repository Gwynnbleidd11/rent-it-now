package com.rentitnow.user.controller;

import com.rentitnow.user.domain.User;
import com.rentitnow.user.domain.UserDTO;
import com.rentitnow.user.mapper.UserMapper;
import com.rentitnow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.mapToUser(userDTO);
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToUserDTO(userService.getUser(userId)));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> customersList = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUserDTOList(customersList));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.mapToUser(userDTO);
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDTO(savedUser));
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
