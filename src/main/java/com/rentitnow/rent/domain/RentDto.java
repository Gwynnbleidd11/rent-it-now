package com.rentitnow.rent.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RentDto(
        Long id,
        Long movieId,
        BigDecimal cost,
        LocalDate rentDate,
        LocalDate returnDate,
        Long userId
) {
}
