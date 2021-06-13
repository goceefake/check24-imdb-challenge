package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.data.MovieRate;
import com.check24.imdbchallenge.data.UserEntity;
import com.check24.imdbchallenge.dto.UserDto;
import com.check24.imdbchallenge.ui.model.UserRatedMoviesResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersServiceImplIT {

    @Autowired
    UsersService usersService;

    @Test
    @Order(1)
    void createUser() throws Exception {
        UserDto user = usersService.createUser(getTempUser());
        assertEquals("test_user", user.getUsername());
    }

    @Test
    @Order(4)
    public void deleteUser() {
        boolean deleted = usersService.deleteUser("test_user");
        assertEquals(true, deleted);
    }

    @Test
    @Order(2)
    void getUserByUserName() {
        UserDto user = usersService.getUserByUserName("test_user");
        assertEquals("test_user", user.getUsername());
    }

    @Test
    @Order(3)
    void getUserRatedMovies() {
        List<UserRatedMoviesResponse> moviesList =
                usersService.getUserRatedMovies("cem");

        assertEquals(1, moviesList.size());
        assertEquals(5, moviesList.get(0).getRate());
        assertEquals("cem", moviesList.get(0).getUser().getUsername());
    }

    private UserDto getTempUser() {
        UserDto dto = new UserDto();
        dto.setPassword("pass");
        dto.setUsername("test_user");
        return dto;
    }

    private UserEntity getTempUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setPassword("pass");
        entity.setUsername("test_user");
        Set<MovieRate> rates = new HashSet<>();
        rates.add(new MovieRate(entity, 3));
        entity.setMovieRates(rates);

        return entity;
    }

}
