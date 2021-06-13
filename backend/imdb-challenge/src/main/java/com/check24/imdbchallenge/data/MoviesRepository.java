package com.check24.imdbchallenge.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviesRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByName(String name);
}
