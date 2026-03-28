package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.UserEntity;
import com.pfm.restapi.repository.UserRepo;
import com.pfm.restapi.security.AuthRequest;
import com.pfm.restapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userAuthRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserEntity createUser(AuthRequest request) {
        log.debug("Inside createUser");
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddedBy(request.getUsername());
        user.setDateAdded(String.valueOf(LocalDateTime.now()));
        return userAuthRepo.save(user);
    }
}
