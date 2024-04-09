package com.rentitnow.rent.domain;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.user.domain.User;
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
@Entity(name = "rents")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID rentId;
    private BigDecimal cost;
    private LocalDate rentDate;
    private LocalDate returnDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(
            targetEntity = Movie.class,
            mappedBy = "rent",
            fetch = FetchType.LAZY
    )
    private List<Movie> listOfRentedMovies;
}
