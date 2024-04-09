package com.rentitnow.movie.mapper;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.domain.MovieDto;
import com.rentitnow.rent.service.RentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieMapper {

    private RentService rentService;

    public Movie mapToMovie(final MovieDto movieDto) {
        return Movie.builder()
                .movieId(movieDto.movieId())
                .title(movieDto.title())
                .director(movieDto.director())
                .cast(movieDto.cast())
                .yearOfPublishing(movieDto.yearOfPublishing())
                .price(movieDto.price())
                .build();
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .yearOfPublishing(movie.getYearOfPublishing())
                .price(movie.getPrice())
                .build();
    }

    public List<MovieDto> mapToMovieDtoList(List<Movie> moviesList) {
        return moviesList.stream()
                .map(this::mapToMovieDto)
                .toList();
    }
}
