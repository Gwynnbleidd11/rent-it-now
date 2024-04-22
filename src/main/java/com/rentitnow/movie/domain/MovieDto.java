package com.rentitnow.movie.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record MovieDto(
        Long movieId,
        String imdbMovieId,
        String title,
        String director,
        String cast,
        LocalDate publicationDate,
        BigDecimal price
) {
}
