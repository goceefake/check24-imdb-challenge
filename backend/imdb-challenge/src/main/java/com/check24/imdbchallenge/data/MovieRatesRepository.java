package com.check24.imdbchallenge.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRatesRepository extends JpaRepository<MovieRate, Long> {
    Optional<MovieRate> findByUserAndMovie(UserEntity user, Movie movies);
}
