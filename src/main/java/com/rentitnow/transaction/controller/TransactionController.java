package com.rentitnow.transaction.controller;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.facade.TransactionFacade;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionDto;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.transaction.mapper.TransactionMapper;
import com.rentitnow.transaction.service.TransactionService;
import com.rentitnow.user.controller.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final TransactionFacade transactionFacade;

    //To delete i guess, transaction is being crerated out of cart
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> createTransaction(@RequestBody TransactionDto transactionDto) throws UserNotFoundException {
//        Transaction transaction = transactionMapper.mapToTransaction(transactionDto);
//        transactionService.saveTransaction(transaction);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping(value = "/{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Long transactionId) throws TransactionNotFountException {
        Transaction transaction = transactionService.getTransaction(transactionId);
        return ResponseEntity.ok(transactionMapper.mapToTransactionDto(transaction));
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<TransactionDto>> getUserTransactions(@PathVariable Long userId) {
        List<Transaction> userTransactions = transactionService.getUserTransactions(userId);
        return ResponseEntity.ok(transactionMapper.mapToTransactionDtoList(userTransactions));
    }

    @PutMapping(value = "/pay/{transactionId}/{cartId}")
    public ResponseEntity<TransactionDto> payTransaction(@PathVariable Long transactionId, @PathVariable Long cartId,@RequestBody TransactionType transactionType) throws CartNotFountException, TransactionNotFountException, UserNotFoundException {
        Transaction transaction = transactionFacade.payTransaction(transactionId, cartId, transactionType);
        return ResponseEntity.ok(transactionMapper.mapToTransactionDto(transaction));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<Transaction> transactionsList = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactionMapper.mapToTransactionDtoList(transactionsList));
    }
}
