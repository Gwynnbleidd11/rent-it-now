package com.rentitnow.movie.domain;

import com.rentitnow.rent.domain.Rent;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private UUID id;
    @NotNull
    private String title;
    private String director;
    private String cast;
    private LocalDate yearOfPublishing;
    private BigDecimal price;
    @ManyToOne
    private Rent rent;
}
