package com.check24.imdbchallenge.controllers;

import com.check24.imdbchallenge.dto.UserDto;
import com.check24.imdbchallenge.service.UserPrincipalDetailService;
import com.check24.imdbchallenge.service.UsersService;
import com.check24.imdbchallenge.ui.model.LoginResponse;
import com.check24.imdbchallenge.ui.model.UserRatedMoviesResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(value = "/signup", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto createdUser = usersService.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping(value="/login", consumes = MediaType.ALL_VALUE)
    public LoginResponse login(@RequestHeader("Authorization") String authorization) {
        byte[] decodedBytes = Base64.getDecoder().decode(authorization.split(" ")[1]);
        String username = new String(decodedBytes).split(":")[0];

        return new LoginResponse(username, true);
    }

    @GetMapping(value="/user/ratedmovies", consumes = MediaType.ALL_VALUE)
    public List<UserRatedMoviesResponse> ratedmovies(@RequestHeader("Authorization") String authorization) {

        byte[] decodedBytes = Base64.getDecoder().decode(authorization.split(" ")[1]);
        String username = new String(decodedBytes).split(":")[0];

        List<UserRatedMoviesResponse> userRatedMovies = usersService.getUserRatedMovies(username);

        return userRatedMovies;
    }

}
