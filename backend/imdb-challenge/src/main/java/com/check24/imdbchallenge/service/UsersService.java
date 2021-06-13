package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.dto.UserDto;
import com.check24.imdbchallenge.ui.model.UserRatedMoviesResponse;

import java.util.List;

public interface UsersService {
	UserDto createUser(UserDto userDetails) throws Exception;
	UserDto getUserByUserName(String username);
	List<UserRatedMoviesResponse> getUserRatedMovies(String username);
	boolean deleteUser(String username);
}
