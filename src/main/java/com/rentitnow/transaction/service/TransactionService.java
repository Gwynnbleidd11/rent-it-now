package com.rentitnow.transaction.service;

import com.rentitnow.cart.domain.Cart;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.transaction.controller.TransactionNotFountException;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

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
        return transactionsList.stream()
                .filter(u -> u.getUser().getUserId().equals(userId))
                .toList();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction payTransaction(final Long transactionId, final Cart cart, TransactionType transactionType) throws TransactionNotFountException {
        Transaction transaction = getTransaction(transactionId);
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        transaction.setTransactionPayed(true);
        transaction.setTransactionDateAndTime(LocalDateTime.now());
        transaction.setTransactionType(transactionType);
        for (Movie movie: cart.getMovies()) {
            sum = sum.add(movie.getPrice());
        }
        transaction.setTransactionValue(sum);
        saveTransaction(transaction);
        return transaction;
    }
}
