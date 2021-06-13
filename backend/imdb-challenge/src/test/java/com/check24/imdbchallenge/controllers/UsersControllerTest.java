package com.check24.imdbchallenge.controllers;

import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.dto.UserDto;
import com.check24.imdbchallenge.service.MoviesService;
import com.check24.imdbchallenge.service.UserPrincipalDetailService;
import com.check24.imdbchallenge.service.UsersService;
import com.check24.imdbchallenge.ui.model.LoginResponse;
import com.check24.imdbchallenge.ui.model.UserRatedMoviesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UsersControllerTest {

    private UsersService usersService;

    private UsersController usersController;

    private UserPrincipalDetailService userPrincipalDetailService;


    @BeforeEach
    void setUp() throws Exception {
        usersService = mock(UsersService.class);
        userPrincipalDetailService = mock(UserPrincipalDetailService.class);

        usersController = new UsersController(usersService);
        when(usersService.getUserByUserName(anyString())).thenReturn(getTempUser());
        when(usersService.getUserRatedMovies(anyString())).thenReturn(Collections.singletonList(getUserRatedMovies()));
        when(usersService.createUser(getTempUser())).thenReturn(getTempUser());
        when(userPrincipalDetailService.loadUserByUsername("cem")).thenReturn(getUserDetails());
    }

    @Test
    void createUser() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<UserDto> createUser = usersController.createUser(getTempUser());

        assertEquals(createUser.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void login() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        LoginResponse login = usersController.login("Basic dXV1OnBhc3M=");

        assertEquals(login.getUsername(), "uuu");
    }

    @Test
    void ratedmovies() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<UserRatedMoviesResponse> ratedmovies = usersController.ratedmovies("Basic dXV1OnBhc3M=");

        assertEquals(ratedmovies.get(0).getUser().getUsername(), "cem");
    }

    private UserDto getTempUser() {
        UserDto dto = new UserDto();
        dto.setPassword("123");
        dto.setUsername("cem");
        return dto;
    }

    private MoviesDto getTempMovie() {
        MoviesDto dto = new MoviesDto();
        dto.setMovieName("Tangerines");
        return dto;
    }

    private UserRatedMoviesResponse getUserRatedMovies() {
        UserRatedMoviesResponse dto = new UserRatedMoviesResponse();
        dto.setMovie(getTempMovie());
        dto.setUser(getTempUser());
        dto.setRate(2);

        return dto;
    }

    private UserDetails getUserDetails() {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return "pass";
            }

            @Override
            public String getUsername() {
                return "cem";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }

}