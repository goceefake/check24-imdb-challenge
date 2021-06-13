package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.data.MovieRate;
import com.check24.imdbchallenge.data.UserEntity;
import com.check24.imdbchallenge.data.UsersRepository;
import com.check24.imdbchallenge.dto.UserDto;
import com.check24.imdbchallenge.ui.model.UserRatedMoviesResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserPrincipalDetailService userPrincipalDetailService;

	public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, UserPrincipalDetailService userPrincipalDetailService) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.userPrincipalDetailService = userPrincipalDetailService;
	}

	@Override
	public UserDto createUser(UserDto userDto) throws Exception {
		UserEntity existingUser = usersRepository.findByusername(userDto.getUsername());
		if (existingUser != null) {
			throw new Exception("User alreay exists");
		}

		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		usersRepository.save(userEntity);

		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

		return returnValue;
	}

	@Override
	public boolean deleteUser(String username) {
		UserEntity existingUser = usersRepository.findByusername(username);
		usersRepository.delete(existingUser);
		return true;
	}

	@Override
	public UserDto getUserByUserName(String username) {
		UserEntity userEntity = usersRepository.findByusername(username);

		if(userEntity == null) throw new UsernameNotFoundException(username);

		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public List<UserRatedMoviesResponse> getUserRatedMovies(String username) {
		ModelMapper mapper = new ModelMapper();

		UserEntity userEntity = usersRepository.findByusername(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);

		Set<MovieRate> ratesEntities = userEntity.getMovieRates();

		List<UserRatedMoviesResponse> result = ratesEntities.stream()
				.map(c -> mapper.map(c, UserRatedMoviesResponse.class))
				.collect(Collectors.toList());

		return result;
	}

}
