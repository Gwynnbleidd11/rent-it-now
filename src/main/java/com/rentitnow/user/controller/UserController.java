package com.rentitnow.user.controller;

import com.rentitnow.user.domain.User;
import com.rentitnow.user.domain.UserDto;
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
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID userId) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToUserDTO(userService.getUser(userId)));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> customersList = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUserDTOList(customersList));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDTO(savedUser));
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
