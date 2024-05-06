package com.rentitnow.transaction.domain;

import com.rentitnow.user.domain.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(unique = true)
    private Long transactionId;
    private LocalDateTime transactionDateAndTime;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal transactionValue;
    @NotNull
    private boolean isTransactionPayed;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
