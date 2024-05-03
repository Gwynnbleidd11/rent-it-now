package com.rentitnow.movie.domain;

import com.rentitnow.cart.domain.Cart;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    @NotNull
    private String imdbMovieId;
    @NotNull
    private String title;
    @NotNull
    private String director;
    @NotNull
    private String cast;
    @NotNull
    private LocalDate publicationDate;
    @NotNull
    private BigDecimal price;
    private String topLevel;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(
            name = "join_movie_cart",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    )
    private List<Cart> carts = new ArrayList<>();

    public static class MovieBuilder {
        private Long movieId;
        private String imdbMovieId;
        private String title;
        private String director;
        private String cast;
        private LocalDate publicationDate;
        private BigDecimal price;
        private String topLevel;
        private List<Cart> carts = new ArrayList<>();

        public MovieBuilder movieId(Long movieId) {
            this.movieId = movieId;
            return this;
        }

        public MovieBuilder imdbMovieId(String imdbMovieId) {
            this.imdbMovieId = imdbMovieId;
            return this;
        }

        public MovieBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder director(String director) {
            this.director = director;
            return this;
        }

        public MovieBuilder cast(String cast) {
            this.cast = cast;
            return this;
        }

        public MovieBuilder publicationDate(LocalDate publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public MovieBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MovieBuilder topLevel(String topLevel) {
            this.topLevel = topLevel;
            return this;
        }

        public MovieBuilder carts(Cart cart) {
            carts.add(cart);
            return this;
        }

        public Movie build() {
            return new Movie(movieId, imdbMovieId, title, director, cast,
                    publicationDate, price, topLevel, carts);
        }
    }

    public Movie(Long movieId, String imdbMovieId, String title, String director, String cast,
                 LocalDate publicationDate, BigDecimal price, String topLevel, List<Cart> carts) {
        this.movieId = movieId;
        this.imdbMovieId = imdbMovieId;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.publicationDate = publicationDate;
        this.price = price;
        this.topLevel = topLevel;
        this.carts = new ArrayList<>(carts);
    }

    public Movie() {
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getImdbMovieId() {
        return imdbMovieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getCast() {
        return cast;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTopLevel() {
        return topLevel;
    }

    public List<Cart> getCarts() {
        return carts;
    }
}
