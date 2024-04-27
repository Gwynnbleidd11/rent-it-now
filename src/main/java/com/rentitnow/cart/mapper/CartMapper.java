package com.rentitnow.cart.mapper;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.domain.CartDto;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.transaction.controller.TransactionNotFountException;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.service.TransactionService;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final CartService cartService;
    private final UserService userService;
    private final TransactionService transactionService;

    public Cart mapToCart(final CartDto cartDto) throws CartNotFountException, UserNotFoundException, TransactionNotFountException {
        Transaction transaction = transactionService.getTransaction(cartDto.transactionId());
        return Cart.builder()
                .cartId(cartDto.cartId())
                .user(userService.getUser(cartDto.userId()))
                .transaction(transaction)
                .movies(cartService.getMoviesFromCart(cartDto.cartId()))
                .build();
    }

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
