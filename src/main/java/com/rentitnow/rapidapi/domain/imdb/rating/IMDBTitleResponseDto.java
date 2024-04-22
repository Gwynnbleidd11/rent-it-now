package com.rentitnow.rapidapi.domain.imdb.rating;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IMDBTitleResponseDto {

    @JsonProperty("ratingsSummary")
    private IMDbRatingSummaryResponseDto ratingsSummary;
}
