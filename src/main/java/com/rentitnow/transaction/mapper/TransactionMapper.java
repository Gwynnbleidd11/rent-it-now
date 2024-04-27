package com.rentitnow.transaction.mapper;

import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionDto;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final UserService userService;

    public Transaction mapToTransaction(final TransactionDto transactionDto) throws UserNotFoundException {
        User user = userService.getUser(transactionDto.userId());
        return Transaction.builder()
                .transactionId(transactionDto.transactionId())
                .transactionDateAndTime(transactionDto.transactionDateAndTime())
                .user(user)
                .transactionValue(transactionDto.transactionValue())
                .isTransactionPayed(transactionDto.isTransactionPayed())
                .transactionType(transactionDto.transactionType())
                .build();
    }

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
