package com.rentitnow.transaction.service;

import com.rentitnow.transaction.controller.TransactionNotFountException;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction saveTransaction(final Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(final Long transactionId) throws TransactionNotFountException {
        return transactionRepository.findByTransactionId(transactionId).orElseThrow(TransactionNotFountException::new);
    }

    public List<Transaction> getUserTransactions(final Long userId) {
        List<Transaction> transactionsList = transactionRepository.findAll();
        transactionsList.stream()
                .filter(u -> u.getUserId().equals(userId))
                .toList();
        return transactionsList;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction payTransaction(final Long transactionId, TransactionType transactionType) throws TransactionNotFountException {
        Transaction transaction = getTransaction(transactionId);
        transaction.setTransactionPayed(true);
        transaction.setTransactionDateAndTime(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.BLIK); //tymczasowo wbite na sztywno
        saveTransaction(transaction);
        return transaction;
    }
}
