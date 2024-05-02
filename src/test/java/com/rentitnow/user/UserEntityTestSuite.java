package com.rentitnow.user;

import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.repository.UserRepository;
import com.rentitnow.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserEntityTestSuite {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveAndGetUser() throws UserNotFoundException {
        //Given
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();

        //When
        userService.saveUser(user);
        User savedUser = userService.getUser(user.getUserId());

        //Then
        assertEquals("Tim", savedUser.getFirstname());
        assertEquals("Stephens", savedUser.getLastname());
        assertEquals("tim@gmail.com", savedUser.getEmail());
    }

    @Test
    public void shouldGetAllUsers() {
        //Given
        List<User> currentUsers = userService.getAllUsers();
        User user1 = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        User user2 = User.builder().firstname("Alex").lastname("Johnson").email("alex@gmail.com")
                .password("987654").build();
        userService.saveUser(user1);
        userService.saveUser(user2);

        //When
        List<User> usersList = userService.getAllUsers();
        int diff = usersList.size() - currentUsers.size();

        //Then
        assertEquals(2, diff);
    }

    @Test
    public void shouldDeleteUser() {
        //Given
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        userService.saveUser(user);

        //When
        userService.deleteUser(user.getUserId());

        //Then
        assertThrows(UserNotFoundException.class, () -> userRepository.findByUserId(user.getUserId()).orElseThrow(UserNotFoundException::new));
    }
}
