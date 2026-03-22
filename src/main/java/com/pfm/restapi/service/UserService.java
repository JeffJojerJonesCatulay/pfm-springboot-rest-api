package com.pfm.restapi.service;

import com.pfm.restapi.entity.UserEntity;
import com.pfm.restapi.security.AuthRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserEntity createUser(AuthRequest request);
}
