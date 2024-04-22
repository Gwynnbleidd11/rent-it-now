package com.rentitnow.movie.controller;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.domain.MovieDto;
import com.rentitnow.movie.mapper.MovieMapper;
import com.rentitnow.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addMovie(@RequestBody MovieDto movieDTto) {
        Movie movie = movieMapper.mapToMovie(movieDTto);
        movieService.saveMovie(movie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long movieId) throws MovieNotFountException {
        return ResponseEntity.ok(movieMapper.mapToMovieDto(movieService.getMovie(movieId)));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        List<Movie> moviesList = movieService.getAllMovies();
        return ResponseEntity.ok(movieMapper.mapToMovieDtoList(moviesList));
    }

    @PutMapping()
    public ResponseEntity<MovieDto> changeMovie(@RequestBody MovieDto movieDto) {
        Movie movie = movieMapper.mapToMovie(movieDto);
        Movie savedMovie = movieService.saveMovie(movie);
        return ResponseEntity.ok(movieMapper.mapToMovieDto(savedMovie));
    }

    @DeleteMapping(value = "{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable final Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }
}
