package com.rentitnow.cart.domain;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(unique = true)
    private UUID cartId;
    private BigDecimal value;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "carts")
    private List<Movie> movies;
}
