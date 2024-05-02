package com.rentitnow.cart.mapper;

import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.domain.CartDto;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final UserService userService;

    public CartDto mapToCartDto(final Cart cart) {
        return CartDto.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUser().getUserId())
                .transactionId(cart.getTransaction() != null ?
                        cart.getTransaction().getTransactionId() : null)
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
