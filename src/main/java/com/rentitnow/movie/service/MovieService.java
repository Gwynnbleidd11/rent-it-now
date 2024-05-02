package com.rentitnow.movie.service;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie saveMovie(final Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie getMovie(final Long movieId) {
        return movieRepository.findByMovieId(movieId);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(final Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
