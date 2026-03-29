package com.pfm.restapi.controller;

import com.pfm.restapi.entity.CCRecordExpenseTracker;
import com.pfm.restapi.entity.RequestLogs;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.inputSanitation.InputSanitation;
import com.pfm.restapi.service.CCRecordExpenseTrackerService;
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
public class CCRecordExpenseTrackerController {
    @Autowired
    private CCRecordExpenseTrackerService service;

    @Value("${api.url.mapping}")
    private String URL;
    private final TpsMonitor tps = new TpsMonitor();
    private static final Logger log = LoggerFactory.getLogger(CCDetailsController.class);
    String httpStatusReturn = "";
    String httpStatusMsgReturn = "";
    InputSanitation inputSanitation = new InputSanitation();
    ResponseEntity<Object> response;

    @Autowired
    private RequestLogsService requestLogsService;

    @GetMapping("/get/cc.record.expense")
    public ResponseEntity<Object> getCCRecordExpense(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ccExpId") String sortBy,
            HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/get/cc.record.expense";
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateSortBy(sortBy);
            inputSanitation.sanitizeInput(sortBy);
            inputSanitation.validateSize(page);
            inputSanitation.validateSize(size);

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<CCRecordExpenseTracker> data = service.getCCExpense(pageable);
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

    @GetMapping("/get/cc.record.expense/ccExpId/{id}")
    public ResponseEntity<Object> getCCRecordExpenseById(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/get/cc.record.expense/ccExpId/" + id;
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));

            List<CCRecordExpenseTracker> data = service.getCCExpenseById(id);
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
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails("ccExpId: " + id);
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

    @PostMapping("/cc.record.expense/create/")
    public ResponseEntity<Object> createCCRecordExpense (@RequestBody CCRecordExpenseTracker ccRecordExpenseTracker, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.record.expense/create/";
        try {
            tps.start(endPoint, " POST METHOD | " + ccRecordExpenseTracker.getCcRecId());
            log.debug("{} API - Start", endPoint);

            inputSanitation.sanitizeInput(ccRecordExpenseTracker.getDate());
            inputSanitation.sanitizeInput(ccRecordExpenseTracker.getExpenseDescription());

            CCRecordExpenseTracker responses = service.createCCExpense(ccRecordExpenseTracker);
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
            String elapsedTime = tps.end( endPoint, " POST METHOD | " + ccRecordExpenseTracker.getCcExpId(), "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("POST");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(ccRecordExpenseTracker.toString());
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("POST METHOD | {} | HTTP STATUS: {} | STATUS : {}", ccRecordExpenseTracker.getCcExpId(), httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @PutMapping("/cc.record.expense/update/{id}")
    public ResponseEntity<Object> updateCCExpenseRecord(@PathVariable Long id, @RequestBody CCRecordExpenseTracker ccRecordExpenseTracker, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.record.expense/update/" + id;

        try {
            tps.start(endPoint, " PUT METHOD | " + id);
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));
            inputSanitation.sanitizeInput(ccRecordExpenseTracker.getDate());
            inputSanitation.sanitizeInput(ccRecordExpenseTracker.getExpenseDescription());

            Optional<CCRecordExpenseTracker> existingCCRecord = service.findById(id);
            if (existingCCRecord.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
            } else {
                CCRecordExpenseTracker responses = service.updateCCExpense(ccRecordExpenseTracker, id);
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
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(ccRecordExpenseTracker.toString() + " PathVariable id: " + id);
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

    @DeleteMapping("/cc.record.expense/delete/{id}")
    public ResponseEntity<Object> deleteCCExpenseRecord(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.details/delete/" + id;
        try {
            tps.start(endPoint, " DELETE METHOD | " + id);
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));
            Optional<CCRecordExpenseTracker> existingCCExpense = service.findById(id);
            if (existingCCExpense.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST);
            } else {
                service.deleteCCExpense(id);
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
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
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

    @GetMapping("/search/cc.record.expense")
    public ResponseEntity<Object> searchCCRecordExpense(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ccExpId") String sortBy,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String expenseDescription,
            HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/search/cc.record.expense";
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateSortBy(sortBy);
            inputSanitation.sanitizeInput(sortBy);
            inputSanitation.validateSize(page);
            inputSanitation.validateSize(size);
            inputSanitation.sanitizeInput(date);
            inputSanitation.sanitizeInput(expenseDescription);

            CCRecordExpenseTracker ccRecordExpenseTracker = new CCRecordExpenseTracker();
            ccRecordExpenseTracker.setDate(date);
            ccRecordExpenseTracker.setExpenseDescription(expenseDescription);

            log.debug("CCRecordExpenseTracker - Search Value: {} ", ccRecordExpenseTracker.toString());

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<CCRecordExpenseTracker> data = service.findByCustomSearch(pageable, ccRecordExpenseTracker);
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
