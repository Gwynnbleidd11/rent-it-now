package com.rentitnow.rent.mapper;

import com.rentitnow.movie.controller.MovieNotFountException;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.service.MovieService;
import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.domain.RentDto;
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

    public Rent mapToRent(final RentDto rentDTO) throws UserNotFoundException, MovieNotFountException {
        User user = userService.getUser(rentDTO.userId());
        Movie movie = movieService.getMovie(rentDTO.movieId());
        return Rent.builder()
                .rentId(rentDTO.id())
                .movie(movie)
                .cost(rentDTO.cost())
                .rentDate(rentDTO.rentDate())
                .returnDate(rentDTO.returnDate())
                .user(user)
                .build();
    }

    public RentDto mapToRentDTO(final Rent rent) {
        return new RentDto(rent.getRentId(), rent.getMovie().getMovieId(), rent.getCost(),
                rent.getRentDate(), rent.getReturnDate(), rent.getUser().getUserId());
    }

    public List<RentDto> mapToRentDtoList(final List<Rent> rentsList) {
        return rentsList.stream()
                .map(this::mapToRentDTO)
                .toList();
    }
}
