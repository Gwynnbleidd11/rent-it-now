package com.rentitnow.cart.service;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.repository.CartRepository;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.repository.MovieRepository;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.service.TransactionService;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final TransactionService transactionService;

    public Cart saveCart(final Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCart(final Long cartId) throws CartNotFountException {
        return cartRepository.findByCartId(cartId).orElseThrow(CartNotFountException::new);
    }

    public void deleteCart(final Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public List<Movie> getMoviesFromCart(final Long cartId) throws CartNotFountException {
        List<Movie> moviesList = cartRepository.findByCartId(cartId).map(Cart::getMovies)
                .orElseThrow(CartNotFountException::new);
        return moviesList;
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
        movie.getCarts().remove(cart);
        movieRepository.save(movie);
        cart.getMovies().remove(movie);
        return cartRepository.save(cart);
    }

    public Transaction createTransactionFromCart(final Cart cart) throws UserNotFoundException {
        User user = userService.getUser(cart.getUser().getUserId());
        Transaction transaction = Transaction.builder()
                .user(user)
                .isTransactionPayed(false)
                .build();
        Transaction transaction1 = transactionService.saveTransaction(transaction);
        cart.setTransaction(transaction1);
        cartRepository.save(cart);
        return transaction;
    }

    public Cart clearCart(final Cart cart) throws CartNotFountException {
        emptyCart(cart);
        clearTransactionFromCart(cart);
        return cartRepository.save(cart);
    }

    private void emptyCart(final Cart cart) throws CartNotFountException {
        List<Movie> moviesList = new ArrayList<>(cart.getMovies());
        for (Movie movie: moviesList) {
            deleteMovieFromCart(cart.getCartId(), movie);
        }
    }

    private void clearTransactionFromCart(final Cart cart) {
        cart.setTransaction(null);
    }
}
