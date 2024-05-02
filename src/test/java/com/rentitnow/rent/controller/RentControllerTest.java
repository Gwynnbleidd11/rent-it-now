package com.rentitnow.rent.controller;

import com.rentitnow.rent.domain.RentDto;
import com.rentitnow.rent.mapper.RentMapper;
import com.rentitnow.rent.service.RentService;
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
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(RentController.class)
class RentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RentService rentService;
    @MockBean
    private RentMapper rentMapper;

    @Test
    public void shouldGetRent() throws Exception {
        //Given
        RentDto rentDto = RentDto.builder().rentId(567L).cost(new BigDecimal("12.45"))
                .rentDate(LocalDate.now()).returnDate(LocalDate.now().plusDays(14))
                .movieId(345L).userId(456L).transactionId(654L).build();
        when(rentMapper.mapToRentDto(rentService.getRent(567L))).thenReturn(rentDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/rents/rent/567")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost", Matchers.is(12.45)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieId", Matchers.is(345)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId", Matchers.is(654)));
    }

    @Test
    public void shouldGetUserRents() throws Exception {
        //Given
        RentDto rentDto1 = RentDto.builder().rentId(567L).cost(new BigDecimal("12.45"))
                .rentDate(LocalDate.now()).returnDate(LocalDate.now().plusDays(14))
                .movieId(345L).userId(456L).transactionId(654L).build();
        RentDto rentDto2 = RentDto.builder().rentId(678L).cost(new BigDecimal("18.55"))
                .rentDate(LocalDate.now()).returnDate(LocalDate.now().plusDays(14))
                .movieId(223L).userId(456L).transactionId(348L).build();
        List<RentDto> userRents = List.of(rentDto1, rentDto2);
        when(rentMapper.mapToRentDtoList(rentService.getUserRents(456L))).thenReturn(userRents);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/rents/user/456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].movieId", Matchers.is(345)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].cost", Matchers.is(18.55)));
    }
}