package com.check24.imdbchallenge.controllers;

import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.service.MoviesService;
import com.check24.imdbchallenge.ui.model.AddRateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:4200")
public class MoviesController {
    private final MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MoviesDto> getMovies() {
        List<MoviesDto> allMovies = moviesService.getAllMovies();
        return allMovies;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MoviesDto> createMovie(@RequestBody MoviesDto movieDto) {
        MoviesDto returnValue = moviesService.createMovie(movieDto);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PostMapping(value="/addRate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MoviesDto> addRate(@RequestHeader("Authorization") String authorization,
                                             @Validated @RequestBody AddRateRequest rateRequest) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(authorization.split(" ")[1]);
        String username = new String(decodedBytes).split(":")[0];

        MoviesDto returnValue = moviesService.addRate(username, rateRequest);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

}
