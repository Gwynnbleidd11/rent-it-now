package com.rentitnow.facade;

import com.rentitnow.cart.controller.CartNotFountException;
import com.rentitnow.cart.domain.Cart;
import com.rentitnow.cart.service.CartService;
import com.rentitnow.rent.service.RentService;
import com.rentitnow.transaction.controller.TransactionNotFountException;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.transaction.service.TransactionService;
import com.rentitnow.user.controller.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionFacade.class);
    private final CartService cartService;
    private final RentService rentService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionFacade(CartService cartService, RentService rentService, TransactionService transactionService) {
        this.cartService = cartService;
        this.rentService = rentService;
        this.transactionService = transactionService;
    }

    public Transaction payTransaction(final Long transactionId, final Long cartId, final TransactionType transactionType) throws CartNotFountException, TransactionNotFountException, UserNotFoundException {
        Cart cart = cartService.getCart(cartId);
        Transaction transaction = transactionService.payTransaction(transactionId, cart, transactionType);
        LOGGER.info("Transaction " + transaction.getTransactionId() + " has been paid");
        rentService.saveRent(cart, transaction);
        cartService.clearCart(cart);
        LOGGER.info("Cart has been cleared!");
        return transaction;
    }
}
