package com.rentitnow.rent.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RentDTO(
        UUID id,
        BigDecimal cost,
        LocalDate rentDate,
        LocalDate returnDate,
        UUID userId
) {
}
