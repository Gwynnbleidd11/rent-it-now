package com.rentitnow.transaction.mapper;

import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.domain.TransactionDto;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTestSuite {

    @InjectMocks
    private TransactionMapper transactionMapper;

    private Transaction transaction1;
    private Transaction transaction2;
    private User user;

    @BeforeEach
    public void prepareData() {
        user = User.builder()
                .firstname("John")
                .lastname("Smith")
                .email("john@gmail.com")
                .password("johnPassword")
                .phoneNumber("123-456-7890")
                .birthDate(LocalDate.of(1985, 2, 15))
                .creationDate(LocalDate.now())
                .build();
        transaction1 = Transaction.builder()
                .isTransactionPayed(true)
                .transactionValue(new BigDecimal("25"))
                .user(user)
                .transactionType(TransactionType.BLIK)
                .transactionDateAndTime(LocalDateTime.now())
                .build();
        transaction2 = Transaction.builder()
                .isTransactionPayed(true)
                .transactionValue(new BigDecimal("40"))
                .user(user)
                .transactionType(TransactionType.CREDIT_DEBIT_CARD)
                .transactionDateAndTime(LocalDateTime.now())
                .build();
    }

    @Test
    public void shouldMapToTransactionDto() {
        //Given

        //When
        TransactionDto mappedTransactionDto = transactionMapper.mapToTransactionDto(transaction1);

        //Then
        assertEquals(BigDecimal.valueOf(25), mappedTransactionDto.transactionValue());
        assertTrue(mappedTransactionDto.isTransactionPayed());
        assertEquals(TransactionType.BLIK, mappedTransactionDto.transactionType());
    }

    @Test
    public void shouldMapToTransactionDtoList() {
        //Given
        List<Transaction> transactionsList = new ArrayList<>();
        transactionsList.add(transaction1);
        transactionsList.add(transaction2);

        //When
        List<TransactionDto> mappedTransactionDtoList = transactionMapper.mapToTransactionDtoList(transactionsList);

        //Then
        assertEquals(2, mappedTransactionDtoList.size());
        assertEquals(TransactionType.CREDIT_DEBIT_CARD, mappedTransactionDtoList.get(1).transactionType());
        assertTrue(mappedTransactionDtoList.get(1).isTransactionPayed());
    }
}
