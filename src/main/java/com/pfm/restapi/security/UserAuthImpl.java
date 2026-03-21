package com.pfm.restapi.security;

import com.pfm.restapi.utility.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserAuthImpl implements UserDetailsService {
    @Autowired
    private UserAuthRepo userAuthRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth user = userAuthRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constant.USER_NOT_FOUND_MSG));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
