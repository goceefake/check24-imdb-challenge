package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.ui.model.AddRateRequest;

import java.util.List;

public interface MoviesService {
    List<MoviesDto> getAllMovies();
    MoviesDto createMovie(MoviesDto moviesDto);
    MoviesDto addRate(String username, AddRateRequest moviesDto) throws Exception;
    List<MoviesDto> getMoviesByName(String name);
}
