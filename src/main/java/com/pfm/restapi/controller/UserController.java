package com.pfm.restapi.controller;

import com.pfm.restapi.entity.RequestLogs;
import com.pfm.restapi.entity.UserEntity;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.AuthRequest;
import com.pfm.restapi.security.inputSanitation.InputSanitation;
import com.pfm.restapi.service.RequestLogsService;
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

import java.util.Map;
import java.util.Objects;

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

    ResponseEntity<Object> response;

    @Autowired
    private RequestLogsService requestLogsService;

    @PostMapping("/authenticate/create")
    public ResponseEntity<Object> createUser(@RequestBody AuthRequest authRequest, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/authenticate/create";
        tps.start(endPoint, " POST METHOD | " + authRequest.getUsername());
        log.debug("{} API - Start", endPoint);
        if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
            log.debug("Inputs: request.getUsername() or request.getPassword() is null");
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        }

        try{
            log.debug("Inputs: request.getUsername(): {}", authRequest.getUsername());
            inputSanitation.sanitizeInput(authRequest.getUsername());

            UserEntity responses = userService.createUser(authRequest);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, responses);
        } catch (BadCredentialsException | DataIntegrityViolationException e){
            log.error(e.getMessage());
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response =  Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end( endPoint, " POST METHOD | " + authRequest.getUsername(), "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("POST");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(authRequest.getUsername());
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("POST METHOD | {} | HTTP STATUS: {} | STATUS : {}", authRequest.getUsername(), httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }
}
