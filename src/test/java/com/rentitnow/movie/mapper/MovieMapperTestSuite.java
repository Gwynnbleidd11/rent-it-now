package com.rentitnow.movie.mapper;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.domain.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MovieMapperTestSuite {

    @InjectMocks
    private MovieMapper movieMapper;

    private Movie movie1;
    private Movie movie2;
    private MovieDto movieDto;

    @BeforeEach
    public void prepareData() {
        movie1 = new Movie.MovieBuilder()
                .title("Star Wars")
                .director("George Lucas")
                .price(new BigDecimal("12"))
                .imdbMovieId("1234")
                .publicationDate(LocalDate.of(1977, 5,25))
                .build();
        movie2 = new Movie.MovieBuilder()
                .title("Dune")
                .director("Denis Villeneuve")
                .price(new BigDecimal("13"))
                .imdbMovieId("5678")
                .publicationDate(LocalDate.of(2021, 10,13))
                .build();
        movieDto = MovieDto.builder()
                .title("Star Wars")
                .director("George Lucas")
                .price(new BigDecimal("12"))
                .imdbMovieId("1234")
                .publicationDate(LocalDate.of(1977, 5,25))
                .build();
    }

    @Test
    public void shouldMapToMovie() {
        //Given

        //When
        Movie mappedMovie = movieMapper.mapToMovie(movieDto);

        //Then
        assertEquals("Star Wars", mappedMovie.getTitle());
        assertEquals(BigDecimal.valueOf(12), mappedMovie.getPrice());
        assertEquals("1234", mappedMovie.getImdbMovieId());
    }

    @Test
    public void shouldMapToMovieDto() {
        //Given

        //When
        MovieDto mappedMovieDto = movieMapper.mapToMovieDto(movie2);

        //Then
        assertEquals("Dune", mappedMovieDto.title());
        assertEquals(BigDecimal.valueOf(13), mappedMovieDto.price());
        assertEquals("5678", mappedMovieDto.imdbMovieId());
    }

    @Test
    public void shouldMapToMovieDtoList() {
        //Given
        List<Movie> moviesList = new ArrayList<>();
        moviesList.add(movie1);
        moviesList.add(movie2);

        //When
        List<MovieDto> mappedMovieDtoList = movieMapper.mapToMovieDtoList(moviesList);

        //Then
        assertEquals(2, mappedMovieDtoList.size());
        assertEquals("Star Wars", mappedMovieDtoList.get(0).title());
        assertEquals("Dune", mappedMovieDtoList.get(1).title());
    }
}
