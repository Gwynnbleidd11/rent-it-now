package com.rentitnow.transaction.mapper;

import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionMapper {

    public Transaction mapToTransaction(final TransactionDto transactionDto) {
        return Transaction.builder()
                .transactionId(transactionDto.transactionId())
                .transactionDateAndTime(transactionDto.transactionDateAndTime())
                .userId(transactionDto.userId())
                .transactionValue(transactionDto.transactionValue())
                .isTransactionPayed(transactionDto.isTransactionPayed())
                .transactionType(transactionDto.transactionType())
                .build();
    }

    public TransactionDto mapToTransactionDto(final Transaction transaction) {
        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .transactionDateAndTime(transaction.getTransactionDateAndTime())
                .userId(transaction.getUserId())
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
