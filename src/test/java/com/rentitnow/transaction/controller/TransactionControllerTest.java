package com.rentitnow.transaction.controller;

import com.google.gson.Gson;
import com.rentitnow.facade.TransactionFacade;
import com.rentitnow.transaction.domain.TransactionDto;
import com.rentitnow.transaction.domain.TransactionType;
import com.rentitnow.transaction.mapper.TransactionMapper;
import com.rentitnow.transaction.service.TransactionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private TransactionFacade transactionFacade;
    @MockBean
    private TransactionMapper transactionMapper;

    @Test
    public void shouldGetTransaction() throws Exception {
        //Given
        TransactionDto transactionDto = TransactionDto.builder().transactionId(123L).userId(456L).build();
        when(transactionMapper.mapToTransactionDto(transactionService.getTransaction(123L)))
                .thenReturn(transactionDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/transactions/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(456)));
    }

    @Test
    public void shouldGetUserTransactions() throws Exception {
        //Given
        TransactionDto transactionDto1 = TransactionDto.builder().transactionId(123L).userId(456L).build();
        TransactionDto transactionDto2 = TransactionDto.builder().transactionId(234L).userId(456L).build();
        List<TransactionDto> userTransactions = List.of(transactionDto1, transactionDto2);
        when(transactionMapper.mapToTransactionDtoList(transactionService.getUserTransactions(456L)))
                .thenReturn(userTransactions);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/transactions/user/456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void shouldPayTransaction() throws Exception {
        //Given
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionId(123L).userId(456L)
                .isTransactionPayed(true)
                .transactionValue(new BigDecimal("33.99"))
                .transactionType(TransactionType.BLIK)
                .build();
        when(transactionMapper.mapToTransactionDto(transactionFacade.payTransaction(123L, 999L, TransactionType.BLIK)))
                .thenReturn(transactionDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(transactionDto.transactionType());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/transactions/pay/123/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isTransactionPayed", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionValue", Matchers.is(33.99)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType", Matchers.is("BLIK")));
    }
}