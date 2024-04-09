package com.rentitnow.movie.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record MovieDTO(
        UUID id,
        String title,
        String director,
        String cast,
        LocalDate yearOfPublishing,
        BigDecimal price,
        UUID rentId
) {
}
