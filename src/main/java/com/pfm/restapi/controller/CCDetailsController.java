package com.pfm.restapi.controller;

import com.pfm.restapi.entity.CCDetails;
import com.pfm.restapi.entity.RequestLogs;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.inputSanitation.InputSanitation;
import com.pfm.restapi.service.CCDetailsService;
import com.pfm.restapi.service.RequestLogsService;
import com.pfm.restapi.utility.Constant;
import com.pfm.restapi.utility.TpsMonitor;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("${api.url.mapping}")
public class CCDetailsController {

    @Autowired
    private CCDetailsService ccDetailsService;

    @Value("${api.url.mapping}")
    private String URL;
    private final TpsMonitor tps = new TpsMonitor();
    private static final Logger log = LoggerFactory.getLogger(AllocationMappingController.class);
    String httpStatusReturn = "";
    String httpStatusMsgReturn = "";
    InputSanitation inputSanitation = new InputSanitation();
    ResponseEntity<Object> response;

    @Autowired
    private RequestLogsService requestLogsService;

    @GetMapping("/get/cc.details")
    public ResponseEntity<Object> getCCDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ccId") String sortBy,
            HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/get/cc.details";
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateSortBy(sortBy);
            inputSanitation.sanitizeInput(sortBy);
            inputSanitation.validateSize(page);
            inputSanitation.validateSize(size);

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<CCDetails> data = ccDetailsService.getCCDetails(pageable);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            response =  Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, data);

        } catch (BadCredentialsException | DataIntegrityViolationException | JwtException | IllegalArgumentException e){
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response =  Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response =  Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end( endPoint, " GET METHOD", "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("GET");
            requestLogs.setRequestMethod("ResponseEntity<Object> getCCDetails");
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails("");
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("GET METHOD | HTTP STATUS: {} | STATUS : {}", httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }
        return response;
    }

    @GetMapping("/get/cc.details/ccId/{id}")
    public ResponseEntity<Object> getCCDetailsById(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/get/cc.details/ccId/{id}";
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));

            List<CCDetails> data = ccDetailsService.getCCDetailsById(id);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, data);
        } catch (BadCredentialsException | DataIntegrityViolationException | JwtException e){
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end( endPoint, " GET METHOD", "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("GET");
            requestLogs.setRequestMethod("ResponseEntity<Object> getCCDetailsById");
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails("ccId: " + id);
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("GET METHOD | HTTP STATUS: {} | STATUS : {}", httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @PostMapping("/cc.details/create/")
    public ResponseEntity<Object> createCCDetails (@RequestBody CCDetails ccDetails, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.details/create/";
        try {
            tps.start(endPoint, " POST METHOD | " + ccDetails.getCcName());
            log.debug("{} API - Start", endPoint);

            inputSanitation.sanitizeInput(ccDetails.getCcName());
            inputSanitation.sanitizeInput(ccDetails.getCcLastDigit());
            inputSanitation.sanitizeInput(ccDetails.getCcAcronym());
            inputSanitation.sanitizeInput(ccDetails.getAddedBy());
            inputSanitation.sanitizeInput(ccDetails.getUpdateBy());

            CCDetails responses = ccDetailsService.createCCDetails(ccDetails);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, responses);

        } catch (BadCredentialsException | DataIntegrityViolationException e){
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end( endPoint, " POST METHOD | " + ccDetails.getCcName(), "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("POST");
            requestLogs.setRequestMethod("ResponseEntity<Object> createCCDetails");
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(ccDetails.toString());
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("POST METHOD | {} | HTTP STATUS: {} | STATUS : {}", ccDetails.getCcName(), httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @PutMapping("/cc.details/update/{id}")
    public ResponseEntity<Object> updateCCDetails(@PathVariable Long id, @RequestBody CCDetails ccDetails, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.details/update/{id}";

        try {
            tps.start(endPoint, " PUT METHOD | " + id);
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));
            inputSanitation.sanitizeInput(ccDetails.getCcName());
            inputSanitation.sanitizeInput(ccDetails.getCcLastDigit());
            inputSanitation.sanitizeInput(ccDetails.getCcAcronym());
            inputSanitation.sanitizeInput(ccDetails.getAddedBy());
            inputSanitation.sanitizeInput(ccDetails.getUpdateBy());

            Optional<CCDetails> existingCCId = ccDetailsService.findById(id);
            if (existingCCId.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
            } else {
                CCDetails responses = ccDetailsService.updateCCDetails(ccDetails, id);
                httpStatusReturn = String.valueOf(HttpStatus.OK);
                httpStatusMsgReturn = Constant.SUCCESS;
                response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, responses);
            }
        } catch (BadCredentialsException | DataIntegrityViolationException e){
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        finally {
            String elapsedTime = tps.end( endPoint, " PUT METHOD | " + id,"HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("PUT");
            requestLogs.setRequestMethod("ResponseEntity<Object> updateCCDetails");
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(ccDetails.toString() + " PathVariable id: " + id);
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("PUT METHOD | {} | HTTP STATUS: {} | STATUS : {}", id, httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @DeleteMapping("/cc.details/delete/{id}")
    public ResponseEntity<Object> deleteCCDetails(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.details/delete/{id}";
        try {
            tps.start(endPoint, " DELETE METHOD | " + id);
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));
            Optional<CCDetails> existingCCDetails = ccDetailsService.findById(id);
            if (existingCCDetails.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST);
            } else {
                ccDetailsService.deleteCCDetails(id);
                httpStatusReturn = String.valueOf(HttpStatus.OK);
                httpStatusMsgReturn = Constant.SUCCESS;
                response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK);
            }
        } catch (BadCredentialsException | DataIntegrityViolationException e){
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            String elapsedTime =tps.end( endPoint, " DELETE METHOD | " + id, "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("DELETE");
            requestLogs.setRequestMethod("ResponseEntity<Object> deleteCCDetails");
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails("id: " + id);
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("DELETE METHOD | {} | HTTP STATUS: {} | STATUS : {}", id, httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }
}
