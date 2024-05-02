package com.rentitnow.cart.controller;

import com.rentitnow.cart.controller.CartController;
import com.rentitnow.cart.domain.CartDto;
import com.rentitnow.cart.mapper.CartMapper;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.facade.CartFacade;
import com.rentitnow.movie.domain.MovieDto;
import com.rentitnow.movie.mapper.MovieMapper;
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

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartService cartService;
    @MockBean
    private CartFacade cartFacade;
    @MockBean
    private CartMapper cartMapper;
    @MockBean
    private MovieMapper movieMapper;

    @Test
    public void shouldGetCart() throws Exception {
        //Given
        CartDto cartDto = CartDto.builder().cartId(999L).userId(123L).build();
        when(cartMapper.mapToCartDto(cartService.getCart(999L))).thenReturn(cartDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/carts/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(123)));
    }

    @Test
    public void shouldGetMoviesFromCart() throws Exception {
        //Given
        MovieDto movieDto1 = MovieDto.builder().movieId(123L).title("Test 1 title").director("Test 1 director").build();
        MovieDto movieDto2 = MovieDto.builder().movieId(456L).title("Test 2 title").director("Test 2 director").build();
        List<MovieDto> moviesList = List.of(movieDto1, movieDto2);
        when(movieMapper.mapToMovieDtoList(cartService.getMoviesFromCart(999L))).thenReturn(moviesList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/carts/movies/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test 1 title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].director", Matchers.is("Test 2 director")));
    }

    @Test
    public void shouldAddMovieToCart() throws Exception {
        //Given
        CartDto cartDto = CartDto.builder().cartId(999L).userId(123L).movieIds(List.of(456L)).build();
        when(cartMapper.mapToCartDto(cartFacade.addMovieToCart(999L, 456L))).thenReturn(cartDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/carts/add/999/456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieIds", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieIds[0]", Matchers.is(456)));
    }

    @Test
    public void shouldDeleteMovieFromCart() throws Exception {
        //Given
        CartDto cartDto = CartDto.builder().cartId(999L).userId(123L).movieIds(List.of()).build();
        when(cartMapper.mapToCartDto(cartFacade.deleteMovieFromCart(999L, 456L))).thenReturn(cartDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/carts/remove/999/456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieIds", Matchers.hasSize(0)));
    }

    @Test
    public void shouldCreateTransaction() throws Exception {
        //Given
        doNothing().when(cartFacade).createTransactionFromCart(999L);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/carts/transaction/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldDeleteTransaction() throws Exception {
        //Given
        doNothing().when(cartService).deleteCart(999L);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/carts/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}