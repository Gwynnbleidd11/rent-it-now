package com.rentitnow.movie;

import com.rentitnow.movie.controller.MovieNotFountException;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.repository.MovieRepository;
import com.rentitnow.movie.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MovieEntityTestSuite {

    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void shouldSaveAndGetMovie() {
        //Given
        Movie movie = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .cast("Mark Hammil, Harrisin Ford, Carrie Fisher").build();

        //When
        movieService.saveMovie(movie);
        Movie savedMovie = movieService.getMovie(movie.getMovieId());

        //Then
        assertEquals("Star Wars", savedMovie.getTitle());
        assertEquals("George Lucas", savedMovie.getDirector());
        assertEquals(movie.getMovieId(), savedMovie.getMovieId());
    }

    @Test
    public void shouldGetAllMovies() {
        //Given
        List<Movie> currentList = movieService.getAllMovies();
        Movie movie1 = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .cast("Mark Hammil, Harrisin Ford, Carrie Fisher").build();
        Movie movie2 = new Movie.MovieBuilder().title("Dune").director("Denis Villeneuve")
                .cast("Timothee Chalamet, Rebecca Ferguson, Zendaya").build();
        movieService.saveMovie(movie1);
        movieService.saveMovie(movie2);

        //When
        List<Movie> moviesList = movieService.getAllMovies();
        int diff = moviesList.size() - currentList.size();

        //Then
        assertEquals(2, diff);
    }

    @Test
    public void shouldDeleteMovie() {
        //Given
        Movie movie = new Movie.MovieBuilder().title("Star Wars").director("George Lucas")
                .cast("Mark Hammil, Harrisin Ford, Carrie Fisher").build();
        movieService.saveMovie(movie);

        //When
        movieService.deleteMovie(movie.getMovieId());

        //Then
        assertThrows(MovieNotFountException.class, () -> movieRepository.findById(movie.getMovieId()).orElseThrow(MovieNotFountException::new));
    }
}
