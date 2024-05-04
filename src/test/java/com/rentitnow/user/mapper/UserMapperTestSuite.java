package com.rentitnow.user.mapper;

import com.rentitnow.user.domain.User;
import com.rentitnow.user.domain.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserMapperTestSuite {

    @InjectMocks
    private UserMapper userMapper;

    private User user1;
    private User user2;
    private UserDto userDto;

    @BeforeEach
    public void prepareData() {
        user1 = User.builder()
                .firstname("John")
                .lastname("Smith")
                .email("john@gmail.com")
                .password("johnPassword")
                .phoneNumber("123-456-7890")
                .birthDate(LocalDate.of(1985, 2, 15))
                .creationDate(LocalDate.now())
                .build();
        user2 = User.builder()
                .firstname("Adrian")
                .lastname("Johnson")
                .email("adrian@gmail.com")
                .password("adrianPassword")
                .phoneNumber("999-999-0987")
                .birthDate(LocalDate.of(1982, 12, 18))
                .creationDate(LocalDate.now())
                .build();
        userDto = UserDto.builder()
                .firstname("Stephan")
                .lastname("Jameson")
                .email("stephan@gmail.com")
                .password("stephanPassword")
                .build();
    }

    @Test
    public void shouldMapToUser() {
        //Given

        //When
        User mappedUser = userMapper.mapToUser(userDto);

        //Then
        assertEquals("Stephan", mappedUser.getFirstname());
        assertEquals("stephan@gmail.com", mappedUser.getEmail());
        assertEquals(userDto.userId(), mappedUser.getUserId());
    }

    @Test
    public void shouldMapToUserDto() {
        //Given

        //When
        UserDto mapperUserDto = userMapper.mapToUserDto(user1);

        //Then
        assertEquals("John", mapperUserDto.firstname());
        assertEquals("123-456-7890", mapperUserDto.phoneNumber());
        assertEquals(LocalDate.of(1985, 2, 15), mapperUserDto.birthDate());
    }

    @Test
    public void shouldMapToUserDtoList() {
        //Given
        List<User> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);

        //When
        List<UserDto> mappedUserDtoList = userMapper.mapToUserDtoList(usersList);

        //Then
        assertEquals(2, mappedUserDtoList.size());
        assertEquals("Johnson", mappedUserDtoList.get(1).lastname());
        assertEquals(LocalDate.of(1982, 12, 18), mappedUserDtoList.get(1).birthDate());
    }
}
