package com.rentitnow.transaction.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionDto(
        Long transactionId,
        LocalDateTime transactionDateAndTime,
        Long userId,
        BigDecimal transactionValue,
        boolean isTransactionPayed,
        TransactionType transactionType) {
}
