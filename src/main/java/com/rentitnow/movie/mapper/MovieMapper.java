package com.rentitnow.movie.mapper;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.domain.MovieDTO;
import com.rentitnow.rent.controller.RentNotFoundException;
import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.service.RentService;
import org.springframework.stereotype.Service;

@Service
public class MovieMapper {

    private RentService rentService;

    public Movie mapToMovie(final MovieDTO movieDTO) throws RentNotFoundException {
        Rent rent = rentService.getRent(movieDTO.id());
        return new Movie(movieDTO.id(), movieDTO.title(), movieDTO.director(),
                movieDTO.cast(), movieDTO.yearOfPublishing(), movieDTO.price(), rent);
    }

    public MovieDTO mapToMovieDTO(final Movie movie) {
        return new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(),
                movie.getCast(), movie.getYearOfPublishing(), movie.getPrice(), movie.getRent().getRentId());
    }
}
