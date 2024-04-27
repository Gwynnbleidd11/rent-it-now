package com.rentitnow.movie.domain;

import com.rentitnow.cart.domain.Cart;
import com.rentitnow.rent.domain.Rent;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    @NotNull
    private String imdbMovieId;
    @NotNull
    private String title;
    @NotNull
    private String director;
    @NotNull
    private String cast;
    @NotNull
    private LocalDate publicationDate;
    @NotNull
    private BigDecimal price;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(
            name = "join_movie_cart",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    )
    private List<Cart> carts = new ArrayList<>();
}
