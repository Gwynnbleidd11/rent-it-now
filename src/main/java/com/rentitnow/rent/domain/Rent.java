package com.rentitnow.rent.domain;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.user.domain.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rents")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;
    @NotNull
    private BigDecimal cost;
    @NotNull
    private LocalDate rentDate;
    @NotNull
    private LocalDate returnDate;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
