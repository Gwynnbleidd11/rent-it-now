package com.rentitnow.movie.repository;

import com.rentitnow.movie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findAll();

    @Override
    Movie save(Movie movie);

    Movie findByMovieId(Long movieId);

    Optional<Movie> findById(Long movieId);


}
