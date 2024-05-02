package com.rentitnow.transaction;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.facade.TransactionFacade;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.service.MovieService;
import com.rentitnow.transaction.controller.TransactionNotFountException;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.transaction.service.TransactionService;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TransactionEntityTestSuite {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionFacade transactionFacade;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private MovieService movieService;

    private Movie movie1;
    private Movie movie2;
    private User user;
    private Cart cart;
    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    private void prepareData() {
        movie1 = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .price(new BigDecimal("11")).build();
        movie2 = new Movie.MovieBuilder().title("Dune").director("Denis Villeneuve")
                .price(new BigDecimal("14")).build();
        user = User.builder().firstname("Alex").lastname("Smith").email("alex@gmail.com").password("123").build();
        cart = Cart.builder().movies(new ArrayList<>()).user(user).build();
        transaction1 = Transaction.builder().user(user).isTransactionPayed(false).build();
        transaction2 = Transaction.builder().transactionValue(new BigDecimal("11"))
                .transactionType(TransactionType.BLIK).isTransactionPayed(true).user(user).build();
        movieService.saveMovie(movie1);
        movieService.saveMovie(movie2);
        userService.saveUser(user);
        cartService.saveCart(cart);
    }

    @Test
    public void shouldSaveAndGetTransaction() throws TransactionNotFountException {
        //Given

        //When
        transactionService.saveTransaction(transaction1);
        Transaction savedTransaction = transactionService.getTransaction(transaction1.getTransactionId());

        //Then
        assertFalse(savedTransaction.isTransactionPayed());
    }

    @Test
    public void shouldGetUserTransactions() {
        //Given
        transactionService.saveTransaction(transaction1);
        transactionService.saveTransaction(transaction2);

        //When
        List<Transaction> userTransactions = transactionService.getUserTransactions(user.getUserId());

        //Then
        assertEquals(2, userTransactions.size());
        assertEquals(TransactionType.BLIK, userTransactions.get(1).getTransactionType());
        assertFalse(userTransactions.get(0).isTransactionPayed());
        assertTrue(userTransactions.get(1).isTransactionPayed());
        assertEquals(BigDecimal.valueOf(11), userTransactions.get(1).getTransactionValue());
    }

    @Test
    public void shouldGetAllTransactions() {
        //Given
        List<Transaction> currentTransactions = transactionService.getAllTransactions();
        User user1 = User.builder().firstname("Tim").lastname("Johnson").email("tim@gmail.com").password("567").build();
        userService.saveUser(user1);
        Transaction transaction3 = Transaction.builder().user(user1).isTransactionPayed(false).build();
        transactionService.saveTransaction(transaction1);
        transactionService.saveTransaction(transaction2);
        transactionService.saveTransaction(transaction3);

        //When
        List<Transaction> transactionsList = transactionService.getAllTransactions();
        int diff = transactionsList.size() - currentTransactions.size();

        //Then
        assertEquals(3, diff);
    }

    @Test
    public void shouldPayTransaction() throws CartNotFountException, TransactionNotFountException, UserNotFoundException {
        //Given
        transactionService.saveTransaction(transaction1);
        cart.getMovies().add(movie1);
        cart.getMovies().add(movie2);
        cartService.saveCart(cart);

        //When
        transactionFacade.payTransaction(transaction1.getTransactionId(), cart.getCartId(), TransactionType.BLIK);

        //Then
        assertTrue(transaction1.isTransactionPayed());
        assertEquals(TransactionType.BLIK, transaction1.getTransactionType());
        assertEquals(BigDecimal.valueOf(25), transaction1.getTransactionValue());
    }
}
