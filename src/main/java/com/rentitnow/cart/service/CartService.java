package com.rentitnow.cart.service;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.repository.CartRepository;
import com.rentitnow.movie.controller.MovieNotFountException;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.repository.MovieRepository;
import com.rentitnow.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MovieRepository movieRepository;

    public Cart saveCart(final Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCart(final Long cartId) throws CartNotFountException {
        return cartRepository.findByCartId(cartId).orElseThrow(CartNotFountException::new);
    }

    public List<Movie> getMoviesFromCart(final Long cartId) throws CartNotFountException {
        List<Movie> moviesList = cartRepository.findByCartId(cartId).map(Cart::getMovies)
                .orElseThrow(CartNotFountException::new);
        return moviesList;
    }

    public Movie getMovieFromCart(final Long cartId, final Long movieId) throws CartNotFountException, MovieNotFountException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(CartNotFountException::new);
        for (Movie movie: cart.getMovies()) {
            if (movie.getMovieId().equals(movieId)) {
                return movie;
            }
        }
        throw new MovieNotFountException();
    }

    public Cart addMovieToCart(final Long cartId, final Movie movie) throws CartNotFountException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(CartNotFountException::new);
        cart.getMovies().add(movie);
        movie.getCarts().add(cart);
        movieRepository.save(movie);
        return cartRepository.save(cart);
    }

    public Cart deleteMovieFromCart(final Long cartId, final Movie movie) throws CartNotFountException {
        Cart cart = cartRepository.findByCartId(cartId).orElseThrow(CartNotFountException::new);
        cart.getMovies().remove(movie);
        movie.getCarts().remove(cart);
        movieRepository.save(movie);
        return cartRepository.save(cart);
    }

    private BigDecimal calculateRentValue(final Cart cart) {
        BigDecimal total = BigDecimal.ZERO;
        for (Movie movie: cart.getMovies()) {
            total = total.add(movie.getPrice());
        }
        return total;
    }

    public Transaction createTransactionFromCart(final Cart cart) {
        return Transaction.builder()
                .userId(cart.getUser().getUserId())
                .transactionValue(calculateRentValue(cart))
                .isTransactionPayed(false)
                .build();
    }

    public Cart createNewCart(final Cart cart) throws CartNotFountException {
        Cart newCart = Cart.builder()
            .movies(new ArrayList<>())
            .build();
        for (Movie movie: cart.getMovies()) {
            deleteMovieFromCart(cart.getCartId(), movie);
        }
        deleteCart(cart.getCartId());
        return newCart;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public void deleteCart(final Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
