package com.rentitnow.user.mapper;

import com.rentitnow.user.domain.User;
import com.rentitnow.user.domain.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public User mapToUser(final UserDTO userDTO) {
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

    public UserDTO mapToUserDTO(final User user) {
        return new UserDTO(user.getUserId(), user.getFirstname(), user.getLastname(), user.getEmail(),
                user.getPassword(), user.getPhoneNumber(), user.getBirthDate(), user.getCreationDate());
    }

    public List<UserDTO> mapToUserDTOList(final List<User> customersList) {
        return customersList.stream()
                .map(this:: mapToUserDTO)
                .toList();
    }
}
