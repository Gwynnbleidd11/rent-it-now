package com.rentitnow.cart.domain;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.user.domain.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(unique = true)
    private Long cartId;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @ManyToMany(mappedBy = "carts", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Movie> movies = new ArrayList<>();
}
