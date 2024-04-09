package com.rentitnow.movie.service;

import com.rentitnow.movie.controller.MovieNotFountException;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    private MovieRepository movieRepository;

    public Movie addMovie(final Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie getMovie(final UUID movieId) throws MovieNotFountException {
        return movieRepository.findById(movieId).orElseThrow(MovieNotFountException::new);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
