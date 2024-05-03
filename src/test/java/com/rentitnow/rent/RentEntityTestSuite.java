package com.rentitnow.rent;

import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.service.MovieService;
import com.rentitnow.rent.controller.RentNotFoundException;
import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.service.RentService;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.transaction.service.TransactionService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class RentEntityTestSuite {

    @Autowired
    private RentService rentService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    private Movie movie1;
    private Movie movie2;
    private User user;
    private Cart cart;
    private Transaction transaction;

    @BeforeEach
    private void prepareData() {
        movie1 = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .price(new BigDecimal("11")).build();
        movie2 = new Movie.MovieBuilder().title("Dune").director("Denis Villeneuve")
                .price(new BigDecimal("14")).build();
        user = User.builder().firstname("Alex").lastname("Smith").email("alex@gmail.com").password("123").build();
        cart = Cart.builder().movies(new ArrayList<>()).user(user).build();
        transaction = Transaction.builder().transactionValue(new BigDecimal("25"))
                .transactionType(TransactionType.BLIK).isTransactionPayed(true).user(user).build();
        movieService.saveMovie(movie1);
        movieService.saveMovie(movie2);
        userService.saveUser(user);
        cart.getMovies().add(movie1);
        cart.getMovies().add(movie2);
        cartService.saveCart(cart);
        transactionService.saveTransaction(transaction);
    }

    @Test
    public void shouldGetUserRents() {
        //Given
        rentService.saveRent(cart, transaction);

        //When
        List<Rent> userRents = rentService.getUserRents(user.getUserId());

        //Then
        assertEquals(2, userRents.size());
        assertEquals("Star Wars", userRents.get(0).getMovie().getTitle());
        assertEquals(BigDecimal.valueOf(14), userRents.get(1).getCost());
    }

    @Test
    public void shouldGetRent() throws RentNotFoundException {
        //Given
        rentService.saveRent(cart, transaction);
        List<Rent> userRents = rentService.getUserRents(user.getUserId());

        //When
        Rent rent = rentService.getRent(userRents.get(0).getRentId());

        //Then
        assertEquals("Star Wars", rent.getMovie().getTitle());
        assertEquals(BigDecimal.valueOf(11), rent.getCost());
    }

    @Test
    public void shouldGetAllRents() {
        //Given
        List<Rent> currentRents = rentService.getAllRents();
        rentService.saveRent(cart, transaction);

        //When
        List<Rent> rentsList = rentService.getAllRents();
        int diff = rentsList.size() - currentRents.size();

        //Then
        assertEquals(2, diff);
    }
}
