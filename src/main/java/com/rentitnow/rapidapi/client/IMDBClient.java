package com.rentitnow.rapidapi.client;

import com.rentitnow.rapidapi.domain.imdb.rating.IMDbRatingResponseDto;
import com.rentitnow.rapidapi.domain.imdb.rating.IMDbRatingSummaryResponseDto;
import com.rentitnow.rapidapi.domain.metacritic.IMDbMetacriticScoreResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class IMDBClient {

    private final RestTemplate restTemplate;

    @Value("${RapidAPI-Key}")
    private String rapidApiKey;
    @Value("${RapidAPI-IMDb-Host}")
    private String imdbRapidApiEndpoint;

    //duplicated code, fix later
    public Integer getMatacriticScore(String imdbMovieId) {
        URI url = UriComponentsBuilder.fromHttpUrl(imdbRapidApiEndpoint + "/title/v2/get-metacritic")
                .queryParam("rapidapi-key", rapidApiKey)
                .queryParam("tconst", imdbMovieId)
                .build().encode().toUri();
        IMDbMetacriticScoreResponseDto dataResponse = restTemplate.getForObject(url, IMDbMetacriticScoreResponseDto.class);
        assert dataResponse != null;
        log.info(String.valueOf(dataResponse.getData().getTitle().getMetacritic().getMetascore().getScore()));
        return dataResponse.getData().getTitle().getMetacritic().getMetascore().getScore();
    }

    public IMDbRatingSummaryResponseDto getIMDbRating(String imdbMovieId) {
        URI url = UriComponentsBuilder.fromHttpUrl(imdbRapidApiEndpoint + "/title/v2/get-ratings")
                .queryParam("rapidapi-key", rapidApiKey)
                .queryParam("tconst", imdbMovieId)
                .build().encode().toUri();
        IMDbRatingResponseDto dataResponse = restTemplate.getForObject(url, IMDbRatingResponseDto.class);
        assert dataResponse != null;
        log.info(String.valueOf(dataResponse.getData().getTitle().getRatingsSummary().getAggregateRating()));
        return dataResponse.getData().getTitle().getRatingsSummary();
    }
}
