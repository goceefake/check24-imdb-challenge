package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.data.*;
import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.ui.model.AddRateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepository moviesRepository;
    private final UsersRepository usersRepository;
    private final MovieRatesRepository movieRatesRepository;

    public MoviesServiceImpl(MoviesRepository moviesRepository, UsersRepository usersRepository
            , MovieRatesRepository movieRatesRepository
    ) {
        this.moviesRepository = moviesRepository;
        this.usersRepository = usersRepository;
        this.movieRatesRepository = movieRatesRepository;
    }

    @Override
    public List<MoviesDto> getAllMovies() {
        ModelMapper mapper = new ModelMapper();
        List<Movie> moviesEntity = moviesRepository.findAll();

        MoviesDto[] movieDtoList = mapper.map(moviesEntity, MoviesDto[].class);

        return Arrays.asList(movieDtoList);
    }

    @Override
    public MoviesDto createMovie(MoviesDto moviesDto) {
        ModelMapper mapper = new ModelMapper();
        Movie moviesEntity = mapper.map(moviesDto, Movie.class);

        moviesRepository.save(moviesEntity);

        return mapper.map(moviesEntity, MoviesDto.class);
    }

    @Override
    public List<MoviesDto> getMoviesByName(String name) {
        ModelMapper mapper = new ModelMapper();
        List<Movie> moviesList = moviesRepository.findByName(name);
        MoviesDto[] movieDtoList = mapper.map(moviesList, MoviesDto[].class);

        return Arrays.asList(movieDtoList);
    }

    @Override
    public MoviesDto addRate(String username, @Validated AddRateRequest rateRequest) throws Exception {
        ModelMapper mapper = new ModelMapper();

        UserEntity userEntity = usersRepository.findByusername(username);
        Movie moviesEntity = moviesRepository.findById(rateRequest.getId()).orElseThrow(() -> new Exception("Movie not found - " + rateRequest.getId()));

        MovieRate movieRate = new MovieRate(userEntity, rateRequest.getRate());
        movieRate.setMovie(moviesEntity);

        MovieRate byUserAndMovie = movieRatesRepository.findByUserAndMovie(userEntity, moviesEntity).orElse(movieRate);
        byUserAndMovie.setRate(rateRequest.getRate());
        movieRatesRepository.save(byUserAndMovie);

        return mapper.map(moviesEntity, MoviesDto.class);
    }

}
