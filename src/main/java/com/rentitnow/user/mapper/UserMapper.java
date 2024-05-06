package com.rentitnow.user.mapper;

import com.rentitnow.user.domain.User;
import com.rentitnow.user.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return User.builder()
                .userId(userDto.userId())
                .email(userDto.email())
                .password(userDto.password())
                .firstname(userDto.firstname())
                .lastname(userDto.lastname())
                .birthDate(userDto.birthDate())
                .phoneNumber(userDto.phoneNumber())
                .creationDate(userDto.creationDate())
                .build();
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(user.getUserId(), user.getEmail(), user.getPassword(),
                user.getFirstname(), user.getLastname(), user.getPhoneNumber(),
                user.getBirthDate(), user.getCreationDate());
    }

    public List<UserDto> mapToUserDtoList(final List<User> customersList) {
        return customersList.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
