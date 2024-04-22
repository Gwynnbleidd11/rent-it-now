package com.rentitnow.rapidapi.controller;

import com.rentitnow.rapidapi.client.IMDBClient;
import com.rentitnow.rapidapi.domain.imdb.rating.IMDbRatingSummaryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/imdb")
@RequiredArgsConstructor
@CrossOrigin("*")
public class IMDbController {

    private final IMDBClient imdbClient;

    @GetMapping(value = "/metacritic/{imdbMovieId}")
    public ResponseEntity<Void> getMetacriticScore(@PathVariable final String imdbMovieId) {
        imdbClient.getMatacriticScore(imdbMovieId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/imdbrating/{imdbMovieId}")
    public ResponseEntity<IMDbRatingSummaryResponseDto> getIMDbRating(@PathVariable final String imdbMovieId) {
        IMDbRatingSummaryResponseDto response = imdbClient.getIMDbRating(imdbMovieId);
        return ResponseEntity.ok(response);
    }
}
