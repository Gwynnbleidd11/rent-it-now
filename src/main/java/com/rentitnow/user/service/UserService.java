package com.rentitnow.user.service;

import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(final User user) {
        return userRepository.save(user);
    }

    public User getUser(final Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }
}
