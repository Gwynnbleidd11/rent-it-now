package com.rentitnow.user.controller;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.mapper.CartMapper;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.domain.UserDto;
import com.rentitnow.user.mapper.UserMapper;
import com.rentitnow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final CartService cartService;
    private final CartMapper cartMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        User user = userMapper.mapToUser(userDto);
        user.setCreationDate(LocalDate.now());
        userService.createUser(user);
        Cart cart = cartMapper.mapToNewCart(user.getUserId());
        cartService.saveCart(cart);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws UserNotFoundException {
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
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
