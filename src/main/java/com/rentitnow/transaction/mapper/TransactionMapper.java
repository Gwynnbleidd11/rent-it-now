package com.rentitnow.transaction.mapper;

import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    public TransactionDto mapToTransactionDto(final Transaction transaction) {
        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .transactionDateAndTime(transaction.getTransactionDateAndTime())
                .userId(transaction.getUser().getUserId())
                .transactionValue(transaction.getTransactionValue())
                .isTransactionPayed(transaction.isTransactionPayed())
                .transactionType(transaction.getTransactionType())
                .build();
    }

    public List<TransactionDto> mapToTransactionDtoList(final List<Transaction> transactionsList) {
        return transactionsList.stream()
                .map(this::mapToTransactionDto)
                .toList();
    }
}
