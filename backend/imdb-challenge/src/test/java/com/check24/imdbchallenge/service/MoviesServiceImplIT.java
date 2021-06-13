package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.ui.model.AddRateRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoviesServiceImplIT {

    @Autowired
    private MoviesService moviesService;

    @Test
    @Order(1)
    void createMovie() {
        MoviesDto movie = moviesService.createMovie(getMovieDto());
        assertEquals("test_movie", movie.getMovieName());
    }

    @Test
    @Order(2)
    void addRate() throws Exception {
        List<MoviesDto> movieList = moviesService.getMoviesByName("Tangerines");
        AddRateRequest req = new AddRateRequest();
        req.setId(movieList.get(0).getId());
        req.setRate(3);
        MoviesDto moviesDto = moviesService.addRate("cem", req);

        assertEquals("Tangerines", moviesDto.getMovieName());
    }

    @Test
    @Order(3)
    void getAllMovies_then_retrun_movie_list() {
        List<MoviesDto> allMovies = moviesService.getAllMovies();

        assertNotNull(allMovies);
        assertThat("movieListCount", 0, lessThan(allMovies.size()));
    }

    @Test
    @Order(4)
    public void getMoviesByName() {
        List<MoviesDto> movieList = moviesService.getMoviesByName("test_movie");
        assertThat("movieListCount", 0, lessThan(movieList.size()));
        assertEquals("test_movie", movieList.get(0).getMovieName());
    }

    private MoviesDto getMovieDto() {
        MoviesDto moviesDto = new MoviesDto();
        moviesDto.setMovieName("test_movie");
        return moviesDto;
    }
}
