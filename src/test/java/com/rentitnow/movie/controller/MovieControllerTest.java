package com.rentitnow.movie.controller;

import com.rentitnow.movie.domain.Movie;
import com.rentitnow.movie.domain.MovieDto;
import com.rentitnow.movie.mapper.MovieMapper;
import com.rentitnow.movie.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MovieService movieService;
    @MockBean
    private MovieMapper movieMapper;

    @Test
    public void shouldCreateMovie() throws Exception {
        //Given
        Movie movie = new Movie.MovieBuilder()
                .movieId(999L)
                .imdbMovieId("123456789")
                .title("Test title")
                .director("Test director")
                .cast("Test cast")
                .publicationDate(LocalDate.of(1999, 12, 12))
                .price(new BigDecimal("1.99"))
                .build();
        MovieDto movieDto = MovieDto.builder()
                .movieId(999L)
                .imdbMovieId("123456789")
                .title("Test title")
                .director("Test director")
                .cast("Test cast")
                .publicationDate(LocalDate.of(1999, 12, 12))
                .price(new BigDecimal("1.99"))
                .build();
        when(movieMapper.mapToMovie(movieDto)).thenReturn(movie);

        String publicationDate = movie.getPublicationDate().toString();
        String jsonMovie = "{"
                + "\"movieId\": 999,"
                + "\"imdbMovieId\": \"123456789\","
                + "\"title\": \"Test title\","
                + "\"director\": \"Test director\","
                + "\"cast\": \"Test cast\","
                + "\"publicationDate\": \"" + publicationDate + "\","
                + "\"price\": 1.99"
                + "}";

        //When & Then
        mockMvc
            .perform(MockMvcRequestBuilders
                .post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonMovie))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldGetMovie() throws Exception {
        //Given
        MovieDto movieDto = MovieDto.builder()
                .movieId(999L)
                .imdbMovieId("123456789")
                .title("Test title")
                .director("Test director")
                .cast("Test cast")
                .publicationDate(LocalDate.of(1999, 12, 12))
                .price(new BigDecimal("1.99"))
                .build();
        when(movieMapper.mapToMovieDto(movieService.getMovie(999L))).thenReturn(movieDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/movies/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cast", Matchers.is("Test cast")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(1.99)));

    }

    @Test
    public void shouldChangeMovie() throws Exception {
        //Given
        MovieDto movieDto = MovieDto.builder()
                .movieId(999L)
                .imdbMovieId("123456789")
                .title("Test title")
                .director("Test director")
                .cast("Test cast")
                .publicationDate(LocalDate.of(1999, 12, 12))
                .price(new BigDecimal("1.99"))
                .build();
        when(movieMapper.mapToMovieDto(movieService.saveMovie(any(Movie.class)))).thenReturn(movieDto);

        String publicationDate = movieDto.publicationDate().toString();
        String jsonMovie = "{"
                + "\"movieId\": 999,"
                + "\"imdbMovieId\": \"123456789\","
                + "\"title\": \"Test title\","
                + "\"director\": \"Test director\","
                + "\"cast\": \"Test cast\","
                + "\"publicationDate\": \"" + publicationDate + "\","
                + "\"price\": 1.99"
                + "}";

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonMovie))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cast", Matchers.is("Test cast")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imdbMovieId", Matchers.is("123456789")));
    }

    @Test
    public void shouldGetEmptyMoviesList() throws Exception {
        //Given
        when(movieMapper.mapToMovieDtoList(movieService.getAllMovies())).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetMoviesList() throws Exception {
        //Given
        MovieDto movieDto1 = MovieDto.builder().title("Title 1 test").director("Director 1 test").cast("Cast 1 test").build();
        MovieDto movieDto2 = MovieDto.builder().title("Title 2 test").director("Director 2 test").cast("Cast 2 test").build();
        List<MovieDto> moviesList = List.of(movieDto1, movieDto2);
        when(movieMapper.mapToMovieDtoList(movieService.getAllMovies())).thenReturn(moviesList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Title 1 test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].director", Matchers.is("Director 2 test")));
    }

    @Test
    public void shouldDeleteMovie() throws Exception {
        //Given
        doNothing().when(movieService).deleteMovie(999L);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/movies/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}