package com.rentitnow.facade;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.movie.controller.MovieNotFountException;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.service.MovieService;
import com.rentitnow.user.controller.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartFacade.class);
    private final CartService cartService;
    private final MovieService movieService;

    @Autowired
    public CartFacade(CartService cartService, MovieService movieService) {
        this.cartService = cartService;
        this.movieService = movieService;
    }

    public Cart addMovieToCart(final Long cartId, final Long movieId) throws CartNotFountException, MovieNotFountException {
        Movie movie = movieService.getMovie(movieId);
        Cart cart = cartService.addMovieToCart(cartId, movie);
        LOGGER.info("Adding movie " + movie.getTitle() + " to cart");
        return cart;
    }

    public Cart deleteMovieFromCart(final Long cartId, final Long movieId) throws MovieNotFountException, CartNotFountException {
        Movie movie = movieService.getMovie(movieId);
        Cart cart = cartService.deleteMovieFromCart(cartId, movie);
        LOGGER.info("Removing movie " + movie.getTitle() + " from cart");
        return cart;
    }

    public void createTransactionFromCart(final Long cartId) throws CartNotFountException, UserNotFoundException {
        Cart cart = cartService.getCart(cartId);
        if (cart.getTransaction() == null) {
            cartService.createTransactionFromCart(cart);
            LOGGER.info("Transaction has been created: " + cart.getTransaction().getTransactionId());
        }
    }
}
