package com.check24.imdbchallenge.ui.model;

import com.check24.imdbchallenge.dto.MoviesDto;
import com.check24.imdbchallenge.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRatedMoviesResponse {
    UserDto user;
    MoviesDto movie;
    int rate;
}
