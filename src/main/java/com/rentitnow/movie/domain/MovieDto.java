package com.rentitnow.movie.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record MovieDto(
        UUID movieId,
        String title,
        String director,
        String cast,
        LocalDate yearOfPublishing,
        BigDecimal price,
        UUID rentId
) {
}
