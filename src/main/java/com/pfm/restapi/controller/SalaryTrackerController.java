package com.pfm.restapi.controller;

import com.pfm.restapi.entity.RequestLogs;
import com.pfm.restapi.entity.SalaryTracker;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.inputSanitation.InputSanitation;
import com.pfm.restapi.service.RequestLogsService;
import com.pfm.restapi.service.SalaryTrackerService;
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
public class SalaryTrackerController {
    @Autowired
    private SalaryTrackerService salaryTrackerService;

    @Value("${api.url.mapping}")
    private String URL;
    private final TpsMonitor tps = new TpsMonitor();
    private static final Logger log = LoggerFactory.getLogger(SalaryTrackerController.class);
    String httpStatusReturn = "";
    String httpStatusMsgReturn = "";

    InputSanitation inputSanitation = new InputSanitation();

    ResponseEntity<Object> response;

    @Autowired
    private RequestLogsService requestLogsService;

    @GetMapping("/get/salarytracker")
    public ResponseEntity<Object> getSalaryTracker(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "salaryId") String sortBy,
            HttpServletRequest httpServletRequest) {
        String endPoint = httpServletRequest.getServerName() + URL + "/get/salarytracker";
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateSortBy(sortBy);
            inputSanitation.sanitizeInput(sortBy);
            inputSanitation.validateSize(page);
            inputSanitation.validateSize(size);

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<SalaryTracker> data = salaryTrackerService.getSalaryTracker(pageable);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, data);
        } catch (BadCredentialsException | DataIntegrityViolationException | JwtException | IllegalArgumentException e) {
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end(endPoint, " GET METHOD", "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("GET");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
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

    @GetMapping("/get/salarytracker/id/{salaryId}")
    public ResponseEntity<Object> getSalaryTrackerBySalaryId(@PathVariable Long salaryId, HttpServletRequest httpServletRequest) {
        String endPoint = httpServletRequest.getServerName() + URL + "/get/salarytracker/id/" + salaryId;
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(salaryId));

            List<SalaryTracker> data = salaryTrackerService.getSalaryTrackerBySalaryId(salaryId);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, data);
        } catch (BadCredentialsException | DataIntegrityViolationException | JwtException e) {
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end(endPoint, " GET METHOD", "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("GET");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails("salaryId: " + salaryId);
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

    @PostMapping("/salarytracker/create/")
    public ResponseEntity<Object> createSalaryTracker(@RequestBody SalaryTracker salaryTracker, HttpServletRequest httpServletRequest) {
        String endPoint = httpServletRequest.getServerName() + URL + "/salarytracker/create/";
        try {
            tps.start(endPoint, " POST METHOD | " + salaryTracker.getStatus());
            log.debug("{} API - Start", endPoint);

            inputSanitation.sanitizeInput(salaryTracker.getDate());
            inputSanitation.sanitizeInput(salaryTracker.getStatus());
            inputSanitation.sanitizeInput(salaryTracker.getAddedBy());
            inputSanitation.sanitizeInput(salaryTracker.getUpdateBy());

            SalaryTracker responses = salaryTrackerService.createSalaryTracker(salaryTracker);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, responses);
        } catch (BadCredentialsException | DataIntegrityViolationException e) {
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end(endPoint, " POST METHOD | " + salaryTracker.getStatus(), "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("POST");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(salaryTracker.toString());
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("POST METHOD | {} | HTTP STATUS: {} | STATUS : {}", salaryTracker.getStatus(), httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @PutMapping("/salarytracker/update/{salaryId}")
    public ResponseEntity<Object> updateSalaryTracker(@PathVariable Long salaryId, @RequestBody SalaryTracker salaryTracker, HttpServletRequest httpServletRequest) {
        String endPoint = httpServletRequest.getServerName() + URL + "/salarytracker/update/" + salaryId;
        try {
            tps.start(endPoint, " PUT METHOD | " + salaryId);
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(salaryId));
            inputSanitation.sanitizeInput(salaryTracker.getDate());
            inputSanitation.sanitizeInput(salaryTracker.getStatus());
            inputSanitation.sanitizeInput(salaryTracker.getAddedBy());
            inputSanitation.sanitizeInput(salaryTracker.getUpdateBy());

            Optional<SalaryTracker> existing = salaryTrackerService.findById(salaryId);
            if (existing.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
            } else {
                SalaryTracker responses = salaryTrackerService.updateSalaryTracker(salaryTracker, salaryId);
                httpStatusReturn = String.valueOf(HttpStatus.OK);
                httpStatusMsgReturn = Constant.SUCCESS;
                response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, responses);
            }
        } catch (BadCredentialsException | DataIntegrityViolationException e) {
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end(endPoint, " PUT METHOD | " + salaryId, "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("PUT");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(salaryTracker.toString() + " PathVariable salaryId: " + salaryId);
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("PUT METHOD | {} | HTTP STATUS: {} | STATUS : {}", salaryId, httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @DeleteMapping("/salarytracker/delete/{salaryId}")
    public ResponseEntity<Object> deleteSalaryTracker(@PathVariable Long salaryId, HttpServletRequest httpServletRequest) {
        String endPoint = httpServletRequest.getServerName() + URL + "/salarytracker/delete/" + salaryId;
        try {
            tps.start(endPoint, " DELETE METHOD | " + salaryId);
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(salaryId));

            Optional<SalaryTracker> existing = salaryTrackerService.findById(salaryId);
            if (existing.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST);
            } else {
                salaryTrackerService.deleteSalaryTracker(salaryId);
                httpStatusReturn = String.valueOf(HttpStatus.OK);
                httpStatusMsgReturn = Constant.SUCCESS;
                response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK);
            }
        } catch (BadCredentialsException | DataIntegrityViolationException e) {
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            String elapsedTime = tps.end(endPoint, " DELETE METHOD | " + salaryId, "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("DELETE");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails("salaryId: " + salaryId);
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("DELETE METHOD | {} | HTTP STATUS: {} | STATUS : {}", salaryId, httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @GetMapping("/search/salarytracker")
    public ResponseEntity<Object> searchSalaryTracker(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "salaryId") String sortBy,
            @RequestParam(required = false) String status,
            HttpServletRequest httpServletRequest) {
        String endPoint = httpServletRequest.getServerName() + URL + "/search/salarytracker";
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateSortBy(sortBy);
            inputSanitation.sanitizeInput(sortBy);
            inputSanitation.validateSize(page);
            inputSanitation.validateSize(size);
            inputSanitation.sanitizeInput(status);

            SalaryTracker salaryTracker = new SalaryTracker();
            salaryTracker.setStatus(status);

            log.debug("SalaryTracker - Search Value: {} ", salaryTracker.toString());

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<SalaryTracker> data = salaryTrackerService.findByCustomSearch(pageable, salaryTracker);
            httpStatusReturn = String.valueOf(HttpStatus.OK);
            httpStatusMsgReturn = Constant.SUCCESS;
            response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, data);
        } catch (BadCredentialsException | DataIntegrityViolationException | JwtException | IllegalArgumentException e) {
            httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            httpStatusReturn = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            httpStatusMsgReturn = Constant.GEN_ERR_MSG;
            log.error(e.getMessage());
            response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        } finally {
            String elapsedTime = tps.end(endPoint, " GET METHOD", "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("GET");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
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
}
