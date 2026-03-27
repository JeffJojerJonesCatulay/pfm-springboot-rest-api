package com.pfm.restapi.controller;

import com.pfm.restapi.entity.RequestLogs;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.AuthRequest;
import com.pfm.restapi.security.AuthResponse;
import com.pfm.restapi.security.JwtService;
import com.pfm.restapi.security.inputSanitation.InputSanitation;
import com.pfm.restapi.service.RequestLogsService;
import com.pfm.restapi.utility.Constant;
import com.pfm.restapi.utility.TpsMonitor;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("${api.url.mapping}")
public class SecurityController {
    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);
    private final TpsMonitor tps = new TpsMonitor();
    @Value("${api.url.mapping}")
    private String URL;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RequestLogsService requestLogsService;

    InputSanitation inputSanitation = new InputSanitation();
    String httpStatusReturn = "";
    String httpStatusMsgReturn = "";

    ResponseEntity<Object> response;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> login(@RequestBody AuthRequest request, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/authenticate";
        tps.start(endPoint, " POST METHOD | " + request.getUsername());
        log.debug("{} API - Start", endPoint);
        if (request.getUsername() == null || request.getPassword() == null) {
            log.debug("Inputs: request.getUsername() or request.getPassword() is null");
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        }

        try {
            log.debug("Inputs: request.getUsername(): {}", request.getUsername());
            inputSanitation.sanitizeInput(request.getUsername());

            log.debug("Calling authenticationManager.authenticate method");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            String token = jwtService.generateToken(request.getUsername());
            if (authentication.isAuthenticated()) {
                AuthResponse authResponse = new AuthResponse(token, Constant.EXPIRES_IN, Constant.AUTH_TYPE);
                httpStatusReturn = String.valueOf(HttpStatus.OK);
                httpStatusMsgReturn = Constant.SUCCESS;
                response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, authResponse);
            } else {
                httpStatusReturn = String.valueOf(HttpStatus.UNAUTHORIZED);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.UNAUTHORIZED, null);
            }
        } catch (BadCredentialsException | IllegalArgumentException e){
            log.error(e.getMessage());
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            log.error(e.getMessage());
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end( endPoint, " POST METHOD | " + request.getUsername(),"HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("POST");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(request.getUsername());
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("POST METHOD | {} | HTTP STATUS: {} | STATUS : {}", request.getUsername(), httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }
}
