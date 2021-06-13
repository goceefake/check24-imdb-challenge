package com.check24.imdbchallenge.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByusername(String username);
}