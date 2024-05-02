package com.rentitnow.rent.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record RentDto(
        Long rentId,
        Long movieId,
        BigDecimal cost,
        LocalDate rentDate,
        LocalDate returnDate,
        Long userId,
        Long transactionId
) {
}
