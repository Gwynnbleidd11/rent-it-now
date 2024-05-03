package com.rentitnow.movie.mapper;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.domain.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMapper {

    public Movie mapToMovie(final MovieDto movieDto) {
        return new Movie.MovieBuilder()
                .movieId(movieDto.movieId())
                .title(movieDto.title())
                .director(movieDto.director())
                .cast(movieDto.cast())
                .publicationDate(movieDto.publicationDate())
                .price(movieDto.price())
                .imdbMovieId(movieDto.imdbMovieId())
                .topLevel(movieDto.topLevel())
                .build();
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .publicationDate(movie.getPublicationDate())
                .price(movie.getPrice())
                .imdbMovieId(movie.getImdbMovieId())
                .topLevel(movie.getTopLevel())
                .build();
    }

    public List<MovieDto> mapToMovieDtoList(List<Movie> moviesList) {
        return moviesList.stream()
                .map(this::mapToMovieDto)
                .toList();
    }
}
