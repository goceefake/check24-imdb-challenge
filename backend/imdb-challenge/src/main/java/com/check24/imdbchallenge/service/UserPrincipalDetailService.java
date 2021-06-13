package com.check24.imdbchallenge.service;

import com.check24.imdbchallenge.data.UserEntity;
import com.check24.imdbchallenge.data.UserPrincipal;
import com.check24.imdbchallenge.data.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailService implements UserDetailsService {

    private UsersRepository usersRepository;

    public UserPrincipalDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = usersRepository.findByusername(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }

}
