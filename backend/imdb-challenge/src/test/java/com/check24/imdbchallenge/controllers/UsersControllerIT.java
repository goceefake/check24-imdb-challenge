package com.check24.imdbchallenge.controllers;

import com.check24.imdbchallenge.ImdbChallengeApplication;
import com.check24.imdbchallenge.dto.UserDto;
import com.check24.imdbchallenge.ui.model.LoginResponse;
import com.check24.imdbchallenge.ui.model.UserRatedMoviesResponse;
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
public class UsersControllerIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createUser() {
        ResponseEntity<UserDto> response =
                this.restTemplate.postForEntity("http://localhost:" + port + "/signup",
                        getTempUser(), UserDto.class);
        UserDto user = response.getBody();

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(getTempUser().getUsername(), response.getBody().getUsername());
    }

    @Test
    void login() {
        String username = "cem";
        ResponseEntity<LoginResponse> response = this.restTemplate.exchange("http://localhost:" + port + "/login/",
                HttpMethod.GET, getEntity(), LoginResponse.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void ratedmovies() {
        ResponseEntity<UserRatedMoviesResponse[]> response = this.restTemplate.exchange("http://localhost:" + port + "/user/ratedmovies",
                HttpMethod.GET, getEntity(), UserRatedMoviesResponse[].class);

        assertEquals(200, response.getStatusCodeValue());
    }

    private HttpEntity getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cem","pass");

        return new HttpEntity(headers);
    }

    private UserDto getTempUser() {
        UserDto dto = new UserDto();
        dto.setPassword("123");
        dto.setUsername("cem_");
        return dto;
    }

}
