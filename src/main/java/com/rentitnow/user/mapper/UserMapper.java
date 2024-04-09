package com.rentitnow.user.mapper;

import com.rentitnow.user.domain.User;
import com.rentitnow.user.domain.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public User mapToUser(final UserDto userDTO) {
        return User.builder()
                .userId(userDTO.id())
                .email(userDTO.email())
                .password(userDTO.password())
                .firstname(userDTO.firstname())
                .lastname(userDTO.lastname())
                .birthDate(userDTO.birthDate())
                .creationDate(userDTO.creationDate())
                .build();
    }

    public UserDto mapToUserDTO(final User user) {
        return new UserDto(user.getUserId(), user.getFirstname(), user.getLastname(), user.getEmail(),
                user.getPassword(), user.getPhoneNumber(), user.getBirthDate(), user.getCreationDate());
    }

    public List<UserDto> mapToUserDTOList(final List<User> customersList) {
        return customersList.stream()
                .map(this:: mapToUserDTO)
                .toList();
    }
}