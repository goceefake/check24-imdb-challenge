package com.check24.imdbchallenge.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class UserPrincipalDetailServiceIT {

    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @Test
    void loadUserByUsername() {
        String username = "cem";
        UserDetails userDetails = userPrincipalDetailService.loadUserByUsername(username);

        assertEquals("cem", userDetails.getUsername());
    }

}
