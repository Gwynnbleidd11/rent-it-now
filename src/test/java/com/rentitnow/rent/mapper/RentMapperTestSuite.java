package com.rentitnow.rent.mapper;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.domain.RentDto;
import com.rentitnow.transaction.domain.Transaction;
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

@ExtendWith(MockitoExtension.class)
public class RentMapperTestSuite {

    @InjectMocks
    private RentMapper rentMapper;

    private Rent rent1;
    private Rent rent2;
    private User user;
    private Movie movie;
    private Transaction transaction;

    @BeforeEach
    public void prepareData() {
        movie = new Movie.MovieBuilder()
                .title("Star Wars")
                .director("George Lucas")
                .price(new BigDecimal("12"))
                .imdbMovieId("1234")
                .publicationDate(LocalDate.of(1977, 5,25))
                .build();
        user = User.builder()
                .firstname("John")
                .lastname("Smith")
                .email("john@gmail.com")
                .password("johnPassword")
                .phoneNumber("123-456-7890")
                .birthDate(LocalDate.of(1985, 2, 15))
                .creationDate(LocalDate.now())
                .build();
        transaction = Transaction.builder()
                .isTransactionPayed(true)
                .transactionValue(new BigDecimal("25"))
                .user(user)
                .transactionType(TransactionType.BLIK)
                .transactionDateAndTime(LocalDateTime.now())
                .build();
        rent1 = Rent.builder()
                .transaction(transaction)
                .movie(movie)
                .user(user)
                .cost(movie.getPrice())
                .rentDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(14))
                .build();
        rent2 = Rent.builder()
                .transaction(transaction)
                .movie(movie)
                .user(user)
                .cost(movie.getPrice())
                .rentDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(14))
                .build();
    }

    @Test
    public void shouldMapToRentDto() {
        //Given

        //When
        RentDto mappedRentDto = rentMapper.mapToRentDto(rent1);

        //Then
        assertEquals(BigDecimal.valueOf(12), mappedRentDto.cost());
        assertEquals(user.getUserId(), mappedRentDto.userId());
        assertEquals(LocalDate.now().plusDays(14), mappedRentDto.returnDate());
    }

    @Test
    public void shouldMapToRentDtoList() {
        //Given
        List<Rent> rentsList = new ArrayList<>();
        rentsList.add(rent1);
        rentsList.add(rent2);

        //When
        List<RentDto> mappedRentDtoList = rentMapper.mapToRentDtoList(rentsList);

        //Then
        assertEquals(2, mappedRentDtoList.size());
        assertEquals(rent1.getRentId(), mappedRentDtoList.get(0).rentId());
        assertEquals(rent2.getTransaction().getTransactionId(), mappedRentDtoList.get(1).transactionId());
    }
}
