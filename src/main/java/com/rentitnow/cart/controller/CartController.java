package com.rentitnow.cart.controller;

import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.domain.CartDto;
import com.rentitnow.cart.mapper.CartMapper;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.facade.CartFacade;
import com.rentitnow.movie.controller.MovieNotFountException;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.domain.MovieDto;
import com.rentitnow.movie.mapper.MovieMapper;
import com.rentitnow.user.controller.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;
    private final MovieMapper movieMapper;
    private final CartFacade cartFacade;

    // Not needed if cart is being created along with user creation, leaving it for now, but will probably delete
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> createCart(@RequestBody CartDto cartDto) throws UserNotFoundException {
//        Cart cart = cartMapper.mapToNewCart(cartDto.userId());
//        cartService.saveCart(cart);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId) throws CartNotFountException {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @GetMapping("/movies/{cartId}")
    public ResponseEntity<List<MovieDto>> getMoviesFromCart(@PathVariable Long cartId) throws CartNotFountException {
        List<Movie> moviesList = cartService.getMoviesFromCart(cartId);
        return ResponseEntity.ok(movieMapper.mapToMovieDtoList(moviesList));
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        List<Cart> cartsList = cartService.getAllCarts();
        return ResponseEntity.ok(cartMapper.mapToCartDtoList(cartsList));
    }

    @PutMapping("/add/{cartId}/{movieId}")
    public ResponseEntity<CartDto> addMovieToCart(@PathVariable Long cartId, @PathVariable Long movieId) throws CartNotFountException, MovieNotFountException {
        Cart cart = cartFacade.addMovieToCart(cartId, movieId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @PutMapping("/remove/{cartId}/{movieId}")
    public ResponseEntity<CartDto> deleteMovieFromCart(@PathVariable Long cartId, @PathVariable Long movieId) throws CartNotFountException, MovieNotFountException {
        Cart cart = cartFacade.deleteMovieFromCart(cartId, movieId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @PostMapping("/transaction/{cartId}")
    public ResponseEntity<Void> createTransactionFromCart(@PathVariable Long cartId) throws CartNotFountException, UserNotFoundException {
        cartFacade.createTransactionFromCart(cartId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
