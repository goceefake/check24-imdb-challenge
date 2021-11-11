package com.check24.imdbchallenge.data;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Movie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private long id2;

    private String name;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    Set<MovieRate> movieRates;

    public Movie(String movieName, MovieRate... movieRates) {
        this.name = movieName;
        for(MovieRate movieRate : movieRates)
            movieRate.setMovie(this);
        this.movieRates = Stream.of(movieRates).collect(Collectors.toSet());
    }


}
