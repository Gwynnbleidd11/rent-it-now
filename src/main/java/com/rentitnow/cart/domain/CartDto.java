package com.rentitnow.cart.domain;

import lombok.*;

import java.util.List;

@Builder
public record CartDto(
        Long cartId,
        Long userId,
        Long transactionId,
        List<Long> movieIds
) {
}
