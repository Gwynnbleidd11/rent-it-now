package com.rentitnow.cart;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.repository.CartRepository;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.facade.CartFacade;
import com.rentitnow.movie.controller.MovieNotFountException;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.service.MovieService;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.service.TransactionService;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CartEntityTestSuite {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartFacade cartFacade;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TransactionService transactionService;

    @Test
    public void shouldCreateAndGetCart() throws CartNotFountException {
        //Given
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        Cart cart = Cart.builder().user(user).build();

        //When
        userService.saveUser(user);
        cartService.saveCart(cart);
        Cart savedCart = cartService.getCart(cart.getCartId());

        //Then
        assertNotEquals(0L, savedCart.getCartId());
        assertEquals("Tim", savedCart.getUser().getFirstname());
        assertEquals("tim@gmail.com", savedCart.getUser().getEmail());
    }

    @Test
    public void shouldGetListOfCarts() {
        //Given
        List<Cart> currentListSize = cartService.getAllCarts();
        User user1 = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        Cart cart1 = Cart.builder().user(user1).build();
        userService.saveUser(user1);
        cartService.saveCart(cart1);
        User user2 = User.builder().firstname("Alex").lastname("Johnson").email("alex@gmail.com")
                .password("987654").build();
        Cart cart2 = Cart.builder().user(user2).build();
        userService.saveUser(user2);
        cartService.saveCart(cart2);

        //When
        List<Cart> cartsList = cartService.getAllCarts();
        int diff = cartsList.size() - currentListSize.size();

        //Then
        assertEquals(2, diff);
    }

    @Test
    public void shouldDeleteCart() {
        //Given
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        Cart cart = Cart.builder().user(user).build();
        userService.saveUser(user);
        cartService.saveCart(cart);

        //When
        cartService.deleteCart(cart.getCartId());

        //Then
        assertThrows(CartNotFountException.class, () -> cartRepository.findByCartId(cart.getCartId()).orElseThrow(CartNotFountException::new));
    }

    @Test
    public void shouldGetMoviesFromCart() throws CartNotFountException {
        //Given
        Movie movie1 = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .cast("Mark Hammil, Harrisin Ford, Carrie Fisher").build();
        Movie movie2 = new Movie.MovieBuilder().title("Dune").director("Denis Villeneuve")
                .cast("Timothee Chalamet, Rebecca Ferguson, Zendaya").build();
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        Cart cart = Cart.builder().user(user).movies(List.of(movie1, movie2)).build();
        movie1.getCarts().add(cart);
        movie2.getCarts().add(cart);
        movieService.saveMovie(movie1);
        movieService.saveMovie(movie2);
        userService.saveUser(user);
        cartService.saveCart(cart);

        //When
        List<Movie> moviesList = cartService.getMoviesFromCart(cart.getCartId());

        //Then
        assertEquals(2, moviesList.size());
        assertEquals("Star Wars", moviesList.get(0).getTitle());
        assertEquals("Denis Villeneuve", moviesList.get(1).getDirector());
    }

    @Test
    public void shouldAddMovieToCart() throws CartNotFountException, MovieNotFountException {
        //Given
        Movie movie = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .cast("Mark Hammil, Harrisin Ford, Carrie Fisher").build();
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        Cart cart = Cart.builder().user(user).movies(new ArrayList<>()).build();
        userService.saveUser(user);
        cartService.saveCart(cart);
        movieService.saveMovie(movie);

        //When
        Cart updatedCart = cartFacade.addMovieToCart(cart.getCartId(), movie.getMovieId());

        //Then
        assertEquals("Star Wars", updatedCart.getMovies().get(0).getTitle());
        assertEquals("George Lucas", updatedCart.getMovies().get(0).getDirector());
    }

    @Test
    public void shouldDeleteMovieFromCart() throws CartNotFountException, MovieNotFountException {
        //Given
        Movie movie1 = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .cast("Mark Hammil, Harrisin Ford, Carrie Fisher").build();
        Movie movie2 = new Movie.MovieBuilder().title("Dune").director("Denis Villeneuve")
                .cast("Timothee Chalamet, Rebecca Ferguson, Zendaya").build();
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        Cart cart = Cart.builder().user(user).movies(new ArrayList<>()).build();
        movie1.getCarts().add(cart);
        movie2.getCarts().add(cart);
        cart.getMovies().add(movie1);
        cart.getMovies().add(movie2);
        movieService.saveMovie(movie1);
        movieService.saveMovie(movie2);
        userService.saveUser(user);
        cartService.saveCart(cart);

        //When
        Cart updatedCart = cartFacade.deleteMovieFromCart(cart.getCartId(), movie1.getMovieId());

        //Then
        assertEquals(1, updatedCart.getMovies().size());
        assertEquals("Timothee Chalamet, Rebecca Ferguson, Zendaya", updatedCart.getMovies().get(0).getCast());
    }

    @Test
    public void shouldCreateTransactionFromCart() throws UserNotFoundException, CartNotFountException {
        //Given
        Movie movie1 = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .cast("Mark Hammil, Harrisin Ford, Carrie Fisher").build();
        Movie movie2 = new Movie.MovieBuilder().title("Dune").director("Denis Villeneuve")
                .cast("Timothee Chalamet, Rebecca Ferguson, Zendaya").build();
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        Cart cart = Cart.builder().user(user).movies(new ArrayList<>()).build();
        movie1.getCarts().add(cart);
        movie2.getCarts().add(cart);
        cart.getMovies().add(movie1);
        cart.getMovies().add(movie2);
        movieService.saveMovie(movie1);
        movieService.saveMovie(movie2);
        userService.saveUser(user);
        cartService.saveCart(cart);

        //When
        cartFacade.createTransactionFromCart(cart.getCartId());
        Cart updatedCart = cartService.getCart(cart.getCartId());

        //Then
        assertNotEquals(null, updatedCart.getTransaction());
    }

    @Test
    public void shouldClearMoviesAndTransactionFromCart() throws CartNotFountException {
        //Given
        Movie movie1 = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .cast("Mark Hammil, Harrisin Ford, Carrie Fisher").build();
        Movie movie2 = new Movie.MovieBuilder().title("Dune").director("Denis Villeneuve")
                .cast("Timothee Chalamet, Rebecca Ferguson, Zendaya").build();
        User user = User.builder().firstname("Tim").lastname("Stephens").email("tim@gmail.com")
                .password("123456").build();
        Transaction transaction = Transaction.builder().user(user).build();
        Cart cart = Cart.builder().user(user).movies(new ArrayList<>()).transaction(transaction).build();
        movie1.getCarts().add(cart);
        movie2.getCarts().add(cart);
        cart.getMovies().add(movie1);
        cart.getMovies().add(movie2);
        movieService.saveMovie(movie1);
        movieService.saveMovie(movie2);
        userService.saveUser(user);
        transactionService.saveTransaction(transaction);
        cartService.saveCart(cart);

        //When
        Cart updatedCart = cartService.clearCart(cart);

        //Then
        assertEquals(0, updatedCart.getMovies().size());
        assertNull(cart.getTransaction());
    }
}
