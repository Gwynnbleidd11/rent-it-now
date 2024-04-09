package com.rentitnow.movie.domain;

import com.rentitnow.cart.domain.Cart;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(unique = true)
    private UUID movieId;
    @NotNull
    private String title;
    private String director;
    private String cast;
    private LocalDate yearOfPublishing;
    private BigDecimal price;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "join_movie_cart",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    )
    private List<Cart> carts;
}
