package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.data.*;
import com.check24.imdbchallenge.dto.UserDto;
import com.check24.imdbchallenge.ui.model.UserRatedMoviesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.configuration.IMockitoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsersServiceImplTest {

    private UsersRepository usersRepository;
    private UserPrincipalDetailService userPrincipalDetailService;
    private UsersServiceImpl usersService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        usersRepository = mock(UsersRepository.class);
        userPrincipalDetailService = mock(UserPrincipalDetailService.class);
        usersService = new UsersServiceImpl(usersRepository, passwordEncoder, userPrincipalDetailService);

        when(usersRepository.save(getTempUserEntity())).thenReturn(getTempUserEntity());
        when(usersRepository.findByusername(anyString())).thenReturn(getTempUserEntity());
    }

    @Test
    void createUser() throws Exception {
        when(usersRepository.findByusername("test_user_unit")).thenReturn(null);
        UserDto user = usersService.createUser(getTempUser());
        assertEquals("test_user_unit", user.getUsername());
    }

    @Test
    void getUserByUserName() {
        UserDto user = usersService.getUserByUserName("test_user_unit");
        assertEquals("test_user_unit", user.getUsername());
    }

    @Test
    void getUserRatedMovies() {
        List<UserRatedMoviesResponse> moviesList =
                usersService.getUserRatedMovies("test_user_unit");

        assertEquals(1, moviesList.size());
        assertEquals(3, moviesList.get(0).getRate());
        assertEquals("test_user_unit", moviesList.get(0).getUser().getUsername());
    }

    private UserDto getTempUser() {
        UserDto dto = new UserDto();
        dto.setPassword("pass");
        dto.setUsername("test_user_unit");
        return dto;
    }

    private UserEntity getTempUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setPassword("pass");
        entity.setUsername("test_user_unit");
        Set<MovieRate> rates = new HashSet<>();
        rates.add(new MovieRate(entity, 3));
        entity.setMovieRates(rates);

        return entity;
    }

}