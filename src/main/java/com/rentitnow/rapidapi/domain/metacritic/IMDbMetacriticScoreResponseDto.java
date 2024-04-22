package com.rentitnow.rapidapi.domain.metacritic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IMDbMetacriticScoreResponseDto {

    @JsonProperty("data")
    private IMDbDataResponseDto data;
}
