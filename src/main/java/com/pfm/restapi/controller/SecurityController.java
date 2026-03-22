package com.pfm.restapi.controller;

import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.AuthRequest;
import com.pfm.restapi.security.AuthResponse;
import com.pfm.restapi.security.JwtService;
import com.pfm.restapi.utility.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class SecurityController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> login(@RequestBody AuthRequest request){
        if (request.getUsername() == null || request.getPassword() == null) {
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            String token = jwtService.generateToken(request.getUsername());
            if (authentication.isAuthenticated()) {
                AuthResponse authResponse = new AuthResponse(token, Constant.EXPIRES_IN, Constant.AUTH_TYPE);
                return Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, authResponse);
            } else {
                return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.UNAUTHORIZED, null);
            }
        } catch (BadCredentialsException e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
