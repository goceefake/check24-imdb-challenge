package com.check24.imdbchallenge.controllers;

import com.check24.imdbchallenge.ImdbChallengeApplication;
import com.check24.imdbchallenge.data.UserEntity;
import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.ui.model.AddRateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ImdbChallengeApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesControllerIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getMovies() {
        ResponseEntity<MoviesDto[]> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/movies", MoviesDto[].class);
        MoviesDto[] movies = response.getBody();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void createMovie() {
        ResponseEntity<MoviesDto> response =
                this.restTemplate.postForEntity("http://localhost:" + port + "/movies",
                        getAllMovies(), MoviesDto.class);
        MoviesDto movies = response.getBody();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void addRate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cem","pass");

        HttpEntity<AddRateRequest> entity = new HttpEntity<>(getAddRateRequest(), headers);

        ResponseEntity<MoviesDto> response =
                this.restTemplate.exchange("http://localhost:" + port + "/movies/addRate", HttpMethod.POST,
                        entity, MoviesDto.class);
        MoviesDto movies = response.getBody();

        assertEquals(200, response.getStatusCodeValue());
    }

    private AddRateRequest getAddRateRequest() {
        AddRateRequest req = new AddRateRequest();
        req.setRate(2);
        req.setId(2L);

        return req;
    }

    private MoviesDto getAllMovies() {
        MoviesDto dto = new MoviesDto();
        dto.setId(2L);
        dto.setMovieName("Tangerines");
        return dto;
    }

}
