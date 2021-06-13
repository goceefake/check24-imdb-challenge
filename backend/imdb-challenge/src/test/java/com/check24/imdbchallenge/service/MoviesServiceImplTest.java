package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.data.*;
import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.dto.UserDto;
import com.check24.imdbchallenge.ui.model.AddRateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MoviesServiceImplTest {

    private MoviesRepository moviesRepository;
    private UsersRepository usersRepository;
    private MovieRatesRepository movieRatesRepository;

    MoviesService service;

    @BeforeEach
    void setUp() {
        moviesRepository = mock(MoviesRepository.class);
        usersRepository = mock(UsersRepository.class);
        movieRatesRepository = mock(MovieRatesRepository.class);
        service = new MoviesServiceImpl(moviesRepository,usersRepository,movieRatesRepository);

        when(moviesRepository.findAll()).thenReturn(Collections.singletonList(getTempMovie()));
        when(moviesRepository.save(getTempMovie())).thenReturn(getTempMovie());
        when(usersRepository.findByusername(anyString())).thenReturn(getTempUserEntity());
        when(moviesRepository.findById(anyLong())).thenReturn(getMovieEntity());
        when(movieRatesRepository.findByUserAndMovie(getTempUserEntity(), getMovieEntity().get())).thenReturn(getMovieRateEntity());
    }

    @Test
    void getAllMovies_then_retrun_movie_list() {
        List<MoviesDto> allMovies = service.getAllMovies();
        assertEquals(1, allMovies.size());
        assertEquals(2, allMovies.get(0).getId());
        assertEquals("Tangerines", allMovies.get(0).getMovieName());
    }

    @Test
    void createMovie() {
        MoviesDto movie = service.createMovie(getTempMovieDto());
        assertEquals(2, movie.getId());
        assertEquals("Tangerines", movie.getMovieName());
    }

    @Test
    void addRate() throws Exception {
        MoviesDto movie = service.addRate("cem", getAddRateReq());
        assertEquals(2, movie.getId());
    }

    private AddRateRequest getAddRateReq() {
        AddRateRequest req = new AddRateRequest();
        req.setId(new Random().nextLong());
        req.setRate(5);
        return req;
    }

    private Optional<Movie> getMovieEntity() {
        Movie movie = new Movie();
        movie.setName("Tangerines");
        movie.setId(2L);
        return Optional.of(movie);
    }

    private Optional<MovieRate> getMovieRateEntity() {
        MovieRate rate = new MovieRate();
        rate.setRate(2);
        return Optional.of(rate);
    }

    private MoviesDto getTempMovieDto() {
        MoviesDto dto = new MoviesDto();
        dto.setMovieName("Tangerines");
        dto.setId(2L);
        return dto;
    }

    private UserDto getTempUser() {
        UserDto dto = new UserDto();
        dto.setPassword("123");
        dto.setUsername("cem");
        return dto;
    }

    private UserEntity getTempUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setPassword("123");
        entity.setUsername("cem");
        return entity;
    }

    private Movie getTempMovie() {
        Movie movie = new Movie();
        movie.setName("Tangerines");
        movie.setId(2L);
        return movie;
    }

}