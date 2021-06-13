package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.data.UserEntity;
import com.check24.imdbchallenge.data.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserPrincipalDetailServiceTest {

    private UsersRepository usersRepository;
    private UserPrincipalDetailService userPrincipalDetailService;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        userPrincipalDetailService = new UserPrincipalDetailService(usersRepository);
        when(usersRepository.findByusername("cem")).thenReturn(getUserEntity());
    }

    @Test
    void loadUserByUsername() {
        String username = "cem";
        UserDetails userDetails = userPrincipalDetailService.loadUserByUsername(username);

        assertEquals("cem", userDetails.getUsername());
    }

    private UserEntity getUserEntity() {
        return new UserEntity("cem");
    }
}