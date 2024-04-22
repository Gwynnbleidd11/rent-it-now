package com.rentitnow.transaction.repository;

import com.rentitnow.transaction.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Override
    Transaction save(Transaction transaction);

    List<Transaction> findAllByUserIdIs(Long userId);

    List<Transaction> findAll();

    Optional<Transaction> findByTransactionId(Long transactionId);
}
