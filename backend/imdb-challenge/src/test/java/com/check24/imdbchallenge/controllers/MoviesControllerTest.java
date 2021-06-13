package com.check24.imdbchallenge.controllers;

import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.service.MoviesService;
import com.check24.imdbchallenge.ui.model.AddRateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MoviesControllerTest {

    private MoviesService moviesService;

    private MoviesController moviesController;

    @BeforeEach
    void setUp() throws Exception {
        moviesService = mock(MoviesService.class);
        moviesController = new MoviesController(moviesService);
        when(moviesService.getAllMovies()).thenReturn(Collections.singletonList(getAllMovies()));
        when(moviesService.createMovie(getAllMovies())).thenReturn(getAllMovies());
        when(moviesService.addRate("uuu", getAddRateReq())).thenReturn(getAllMovies());
    }

    @Test
    void getMovies() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<MoviesDto> movies = moviesController.getMovies();

        assertEquals("Tangerines", movies.get(0).getMovieName());
    }

    @Test
    void createMovie() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<MoviesDto> movie = moviesController.createMovie(getAllMovies());

        assertEquals(movie.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void addRate() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<MoviesDto> addRate = moviesController.addRate("Basic dXV1OnBhc3M=", getAddRateReq());

        assertEquals(addRate.getStatusCode(), HttpStatus.OK);
    }

    private AddRateRequest getAddRateReq() {
        AddRateRequest req = new AddRateRequest();
        req.setId(new Random().nextLong());
        req.setRate(5);
        return req;
    }

    private MoviesDto getAllMovies() {
        MoviesDto dto = new MoviesDto();
        dto.setId(1L);
        dto.setMovieName("Tangerines");
        return dto;
    }

}