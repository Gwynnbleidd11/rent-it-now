package com.rentitnow.user.controller;

import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.mapper.CartMapper;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.domain.UserDto;
import com.rentitnow.user.mapper.UserMapper;
import com.rentitnow.user.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private CartService cartService;
    @MockBean
    private CartMapper cartMapper;

    @Test
    public void shouldCreateUserAndCart() throws Exception {
        //Given
        User user = User.builder().userId(999L).firstname("Alex").lastname("Novak")
                .email("Test email").password("Test password").creationDate(LocalDate.now()).build();
        UserDto userDto = UserDto.builder().userId(999L).firstname("Alex").lastname("Novak")
                .email("Test email").password("Test password").creationDate(LocalDate.now()).build();
        when(userMapper.mapToUser(userDto)).thenReturn(user);

        Cart cart = Cart.builder().cartId(888L).user(user).build();
        when(cartMapper.mapToNewCart(user.getUserId())).thenReturn(cart);

        String creationDate = user.getCreationDate().toString();
        String jsonUser = "{"
                + "\"userId\": 999,"
                + "\"firstname\": \"Alex\","
                + "\"lastname\": \"Novak\","
                + "\"email\": \"Test email\","
                + "\"password\": \"Test password\","
                + "\"creationDate\": \"" + creationDate + "\""
                + "}";

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonUser))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void shouldGetUser() throws Exception {
        //Given
        UserDto userDto = UserDto.builder().userId(999L).firstname("Alex").lastname("Novak")
                .email("Test email").password("Test password").creationDate(LocalDate.now()).build();
        when(userMapper.mapToUserDto(userService.getUser(999L))).thenReturn(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("Alex")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.is("Novak")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("Test email")));
    }

    @Test
    public void shouldGetUsersList() throws Exception {
        //Given
        UserDto userDto1 = UserDto.builder().userId(999L).firstname("Alex").lastname("Novak")
                .email("Alex email").password("Alex password").creationDate(LocalDate.now()).build();
        UserDto userDto2 = UserDto.builder().userId(998L).firstname("Adrian").lastname("Kovalsky")
                .email("Adrian email").password("Adrian password").creationDate(LocalDate.now()).build();
        List<UserDto> usersList = List.of(userDto1, userDto2);
        when(userMapper.mapToUserDtoList(userService.getAllUsers())).thenReturn(usersList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname", Matchers.is("Alex")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastname", Matchers.is("Kovalsky")));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        //Given
        UserDto userDto = UserDto.builder().userId(999L).firstname("Alex").lastname("Novak")
                .email("Test email").password("Test password").creationDate(LocalDate.now()).build();
        when(userMapper.mapToUserDto(userService.saveUser(any(User.class)))).thenReturn(userDto);

        String creationDate = userDto.creationDate().toString();
        String jsonUser = "{"
                + "\"userId\": 999,"
                + "\"firstname\": \"Alex\","
                + "\"lastname\": \"Novak\","
                + "\"email\": \"Test email\","
                + "\"password\": \"Test password\","
                + "\"creationDate\": \"" + creationDate + "\""
                + "}";

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("Alex")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("Test email")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("Test password")));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //Given
        doNothing().when(userService).deleteUser(999L);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/users/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }
}