package com.rentitnow.transaction.controller;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.rent.service.RentService;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionDto;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.transaction.mapper.TransactionMapper;
import com.rentitnow.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final CartService cartService;
    private final RentService rentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.mapToTransaction(transactionDto);
        transactionService.saveTransaction(transaction);
        return ResponseEntity.ok().build();
    }

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
    public ResponseEntity<TransactionDto> payTransaction(@PathVariable Long transactionId, @PathVariable Long cartId, TransactionType transactionType) throws CartNotFountException, TransactionNotFountException {
        Transaction transaction = transactionService.payTransaction(transactionId, transactionType);
        Cart cart = cartService.getCart(cartId);
        rentService.rentMovie(cart);
        cartService.createNewCart(cart);
        return ResponseEntity.ok(transactionMapper.mapToTransactionDto(transaction));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<Transaction> transactionsList = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactionMapper.mapToTransactionDtoList(transactionsList));
    }
}
