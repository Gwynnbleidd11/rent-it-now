package com.rentitnow.cart.mapper;

import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.domain.CartDto;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartMapperTestSuite {

    @InjectMocks
    private CartMapper cartMapper;
    @Mock
    private UserService userService;

    private User user;
    private Movie movie1;
    private Movie movie2;
    private Transaction transaction;
    private Cart cart1;
    private Cart cart2;

    @BeforeEach
    public void prepareData() {
        user = User.builder()
                .firstname("John")
                .lastname("Smith")
                .email("john@gmail.com")
                .password("johnPassword")
                .phoneNumber("123-456-7890")
                .birthDate(LocalDate.of(1985, 2, 15))
                .creationDate(LocalDate.now())
                .build();
        movie1 = new Movie.MovieBuilder()
                .title("Star Wars")
                .director("George Lucas")
                .price(new BigDecimal("12"))
                .imdbMovieId("1234")
                .publicationDate(LocalDate.of(1977, 5,25))
                .build();
        movie2 = new Movie.MovieBuilder()
                .title("Dune")
                .director("Denis Villeneuve")
                .price(new BigDecimal("13"))
                .imdbMovieId("5678")
                .publicationDate(LocalDate.of(2021, 10,13))
                .build();
        transaction = Transaction.builder()
                .isTransactionPayed(true)
                .transactionValue(new BigDecimal("25"))
                .user(user)
                .transactionType(TransactionType.BLIK)
                .transactionDateAndTime(LocalDateTime.now())
                .build();
        cart1 = Cart.builder()
                .user(user)
                .transaction(transaction)
                .movies(new ArrayList<>())
                .build();
        cart1.getMovies().add(movie1);
        cart1.getMovies().add(movie2);
        cart2 = Cart.builder()
                .user(user)
                .transaction(transaction)
                .movies(new ArrayList<>())
                .build();
        cart2.getMovies().add(movie1);
        cart2.getMovies().add(movie2);
    }

    @Test
    public void shouldMapToNewCart() throws UserNotFoundException {
        //Given
        when(userService.getUser(user.getUserId())).thenReturn(user);

        //When
        Cart cart = cartMapper.mapToNewCart(user.getUserId());

        //Then
        assertEquals(0, cart.getMovies().size());
        assertEquals(user, cart.getUser());
    }

    @Test
    public void shouldMapToCartDto() {
        //Given

        //When
        CartDto mappedCartDto = cartMapper.mapToCartDto(cart1);

        //Then
        assertEquals(cart1.getCartId(), mappedCartDto.cartId());
        assertEquals(2, mappedCartDto.movieIds().size());
        assertEquals(cart1.getMovies().get(0).getMovieId(), mappedCartDto.movieIds().get(0));
    }

    @Test
    public void shouldMapToCartDtoList() {
        //Given
        List<Cart> cartsList = new ArrayList<>();
        cartsList.add(cart1);
        cartsList.add(cart2);

        //When
        List<CartDto> mappedCartDtoList = cartMapper.mapToCartDtoList(cartsList);

        //Then
        assertEquals(2, mappedCartDtoList.size());
        assertEquals(transaction.getTransactionId(), mappedCartDtoList.get(0).transactionId());
        assertEquals(movie2.getMovieId(), mappedCartDtoList.get(0).movieIds().get(1));
    }
}
