package com.rentitnow.cart.mapper;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.domain.CartDto;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final CartService cartService;
    private  final UserService userService;

    public Cart mapToCart(final CartDto cartDto) throws CartNotFountException, UserNotFoundException {
        return Cart.builder()
                .cartId(cartDto.cartId())
                .user(userService.getUser(cartDto.userId()))
                .movies(cartService.getMoviesFromCart(cartDto.cartId()))
                .build();
    }

    public CartDto mapToCartDto(final Cart cart) {
        return CartDto.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUser().getUserId())
                .movieIds(cart.getMovies().stream()
                        .map(Movie::getMovieId)
                        .toList())
                .build();
    }

    public Cart mapToNewCart(final Long userId) throws UserNotFoundException {
        return Cart.builder()
                .user(userService.getUser(userId))
                .movies(new ArrayList<>())
                .build();
    }

    public List<CartDto> mapToCartDtoList(List<Cart> cartsList) {
        return cartsList.stream()
                .map(this::mapToCartDto)
                .toList();
    }
}
