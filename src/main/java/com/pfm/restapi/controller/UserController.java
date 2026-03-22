package com.pfm.restapi.controller;

import com.pfm.restapi.entity.UserEntity;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.AuthRequest;
import com.pfm.restapi.service.UserService;
import com.pfm.restapi.utility.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.url.mapping}")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate/create")
    public ResponseEntity<Object> createUser(@RequestBody AuthRequest authRequest){
        if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        }

        try{
            UserEntity response = userService.createUser(authRequest);
            return Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, response);
        } catch (BadCredentialsException | DataIntegrityViolationException e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
