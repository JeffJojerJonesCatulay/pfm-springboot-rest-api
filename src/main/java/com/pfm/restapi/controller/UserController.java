package com.pfm.restapi.controller;

import com.pfm.restapi.entity.UserEntity;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.AuthRequest;
import com.pfm.restapi.security.inputSanitation.InputSanitation;
import com.pfm.restapi.service.UserService;
import com.pfm.restapi.utility.Constant;
import com.pfm.restapi.utility.TpsMonitor;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${api.url.mapping}")
    private String URL;
    private final TpsMonitor tps = new TpsMonitor();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    String httpStatusReturn = "";
    String httpStatusMsgReturn = "";

    InputSanitation inputSanitation = new InputSanitation();

    @PostMapping("/authenticate/create")
    public ResponseEntity<Object> createUser(@RequestBody AuthRequest authRequest, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/authenticate/create";
        tps.start(endPoint, " POST METHOD | " + authRequest.getUsername());
        log.debug("{} API - Start", endPoint);
        if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
            log.debug("Inputs: request.getUsername() or request.getPassword() is null");
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        }

        try{
            log.debug("Inputs: request.getUsername(): {}", authRequest.getUsername());
            inputSanitation.sanitizeInput(authRequest.getUsername());

            UserEntity response = userService.createUser(authRequest);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            return Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, response);
        } catch (BadCredentialsException | DataIntegrityViolationException e){
            log.error(e.getMessage());
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            tps.end( endPoint, " POST METHOD | " + authRequest.getUsername(), "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);
            log.debug("POST METHOD | {} | HTTP STATUS: {} | STATUS : {}", authRequest.getUsername(), httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }
    }
}
