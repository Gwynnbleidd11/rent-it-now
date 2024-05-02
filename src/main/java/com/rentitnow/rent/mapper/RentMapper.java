package com.rentitnow.rent.mapper;

import com.rentitnow.movie.controller.MovieNotFountException;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.service.MovieService;
import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.domain.RentDto;
import com.rentitnow.transaction.controller.TransactionNotFountException;
import com.rentitnow.transaction.domain.Transaction;
import com.rentitnow.transaction.service.TransactionService;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentMapper {

    private final UserService userService;
    private final MovieService movieService;
    private final TransactionService transactionService;

    public Rent mapToRent(final RentDto rentDto) throws UserNotFoundException, MovieNotFountException, TransactionNotFountException {
        User user = userService.getUser(rentDto.userId());
        Movie movie = movieService.getMovie(rentDto.movieId());
        Transaction transaction = transactionService.getTransaction(rentDto.transactionId());
        return Rent.builder()
                .rentId(rentDto.rentId())
                .movie(movie)
                .cost(rentDto.cost())
                .rentDate(rentDto.rentDate())
                .returnDate(rentDto.returnDate())
                .user(user)
                .transaction(transaction)
                .build();
    }

    public RentDto mapToRentDto(final Rent rent) {
        return new RentDto(rent.getRentId(), rent.getMovie().getMovieId(), rent.getCost(),
                rent.getRentDate(), rent.getReturnDate(), rent.getUser().getUserId(),
                rent.getTransaction().getTransactionId());
    }

    public List<RentDto> mapToRentDtoList(final List<Rent> rentsList) {
        return rentsList.stream()
                .map(this::mapToRentDto)
                .toList();
    }
}
