package com.pfm.restapi.controller;

import com.pfm.restapi.entity.AllocationMapping;
import com.pfm.restapi.entity.CCConnectedApp;
import com.pfm.restapi.entity.RequestLogs;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.inputSanitation.InputSanitation;
import com.pfm.restapi.service.CCConnectedAppService;
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
import org.springframework.data.domain.PageImpl;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.url.mapping}")
public class CCConnectedAppController {

    @Autowired
    private CCConnectedAppService service;

    @Value("${api.url.mapping}")
    private String URL;
    private final TpsMonitor tps = new TpsMonitor();
    private static final Logger log = LoggerFactory.getLogger(CCConnectedAppController.class);
    String httpStatusReturn = "";
    String httpStatusMsgReturn = "";

    InputSanitation inputSanitation = new InputSanitation();

    ResponseEntity<Object> response;

    @Autowired
    private RequestLogsService requestLogsService;

    @GetMapping("/get/cc.connectedApp")
    public ResponseEntity<Object> getCCConnectedApp(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            HttpServletRequest httpServletRequest){

        String endPoint = httpServletRequest.getServerName() + URL + "/get/cc.connectedApp";
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateSortBy(sortBy);
            inputSanitation.sanitizeInput(sortBy);
            inputSanitation.validateSize(page);
            inputSanitation.validateSize(size);

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<CCConnectedApp> data = service.getCCConnectedApp(pageable);
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

    @GetMapping("/get/cc.connectedApp/id/{id}")
    public ResponseEntity<Object> getCCConnectedAppById(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/get/cc.connectedApp/id/" + id;
        try{
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));

            List<CCConnectedApp> data = service.getCCConnectedAppById(id);
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
            String elapsedTime = tps.end( endPoint, " GET METHOD","HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("GET");
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

            log.debug("GET METHOD | HTTP STATUS: {} | STATUS : {}", httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @PostMapping("/cc.connectedApp/create/")
    public ResponseEntity<Object> createConnectedApp(@RequestBody CCConnectedApp ccConnectedApp, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.connectedApp/create/";
        try {
            tps.start(endPoint, " POST METHOD | " + ccConnectedApp.getConnectedApp());
            log.debug("{} API - Start", endPoint);

            inputSanitation.sanitizeInput(ccConnectedApp.getConnectedApp());
            inputSanitation.sanitizeInput(ccConnectedApp.getSubscription());
            inputSanitation.sanitizeInput(ccConnectedApp.getAutoDebit());
            inputSanitation.sanitizeInput(ccConnectedApp.getRemarks());
            inputSanitation.sanitizeInput(ccConnectedApp.getAddedBy());
            inputSanitation.sanitizeInput(ccConnectedApp.getUpdateBy());

            CCConnectedApp responses = service.createConnectedApp(ccConnectedApp);
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
            String elapsedTime = tps.end( endPoint, " POST METHOD | " + ccConnectedApp.getConnectedApp(), "HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("POST");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(ccConnectedApp.toString());
            requestLogs.setRequestResponse(Objects.requireNonNull(body).toString());
            requestLogs.setStatusCode(Integer.parseInt(httpStatusReturn.replaceAll("\\D+", "")));
            requestLogs.setStatusResponse(httpStatusMsgReturn);
            requestLogs.setTimestamp((String) body.get("timestamp"));
            requestLogs.setTps(elapsedTime);
            requestLogsService.inputLogs(requestLogs);
            log.debug("Done saving request to API Request Table");

            log.debug("POST METHOD | {} | HTTP STATUS: {} | STATUS : {}", ccConnectedApp.getConnectedApp(), httpStatusReturn, httpStatusMsgReturn);
            log.debug("{} API - End", endPoint);
        }

        return response;
    }

    @PutMapping("/cc.connectedApp/update/{id}")
    public ResponseEntity<Object> updateConnectedApp(@PathVariable Long id, @RequestBody CCConnectedApp ccConnectedApp, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.connectedApp/update/" + id;
        try{
            tps.start(endPoint, " PUT METHOD | " + id);
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));
            inputSanitation.sanitizeInput(ccConnectedApp.getConnectedApp());
            inputSanitation.sanitizeInput(ccConnectedApp.getSubscription());
            inputSanitation.sanitizeInput(ccConnectedApp.getAutoDebit());
            inputSanitation.sanitizeInput(ccConnectedApp.getRemarks());
            inputSanitation.sanitizeInput(ccConnectedApp.getAddedBy());
            inputSanitation.sanitizeInput(ccConnectedApp.getUpdateBy());

            Optional<CCConnectedApp> existingConnected = service.findById(id);
            if (existingConnected.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
            } else {
                CCConnectedApp responses = service.updateConnectedApp(ccConnectedApp, id);
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
        } finally {
            String elapsedTime = tps.end( endPoint, " PUT METHOD | " + id,"HTTP STATUS: " + httpStatusReturn + " | STATUS : " + httpStatusMsgReturn);

            log.debug("Starting saving request to API Request Table");
            RequestLogs requestLogs = new RequestLogs();
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            requestLogs.setApiMethod("PUT");
            requestLogs.setRequestMethod(new Exception().getStackTrace()[0].getMethodName());
            requestLogs.setEndpoint(endPoint);
            requestLogs.setRequestDetails(ccConnectedApp.toString() + " PathVariable id: " + id);
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

    @DeleteMapping("/cc.connectedApp/delete/{id}")
    public ResponseEntity<Object> deleteAllocation(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String endPoint = httpServletRequest.getServerName() + URL + "/cc.connectedApp/delete/" + id;
        try{
            tps.start(endPoint, " DELETE METHOD | " + id);
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));

            Optional<CCConnectedApp> existingConnected = service.findById(id);
            if (existingConnected.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST);
            } else {
                service.deleteAllocation(id);
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

    @GetMapping("/search/cc.connectedApp")
    public ResponseEntity<Object> searchCCConnectedApp(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String connectedApp,
            @RequestParam(required = false) String subscription,
            @RequestParam(required = false) String autoDebit,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String remarks,
            HttpServletRequest httpServletRequest){

        String endPoint = httpServletRequest.getServerName() + URL + "/search/cc.connectedApp";
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateSortBy(sortBy);
            inputSanitation.sanitizeInput(sortBy);
            inputSanitation.validateSize(page);
            inputSanitation.validateSize(size);
            inputSanitation.sanitizeInput(connectedApp);
            inputSanitation.sanitizeInput(subscription);
            inputSanitation.sanitizeInput(autoDebit);
            inputSanitation.sanitizeInput(date);
            inputSanitation.sanitizeInput(remarks);

            CCConnectedApp ccConnectedApp = new CCConnectedApp();
            ccConnectedApp.setConnectedApp(connectedApp);
            ccConnectedApp.setSubscription(subscription);
            ccConnectedApp.setAutoDebit(autoDebit);
            ccConnectedApp.setDate(date);
            ccConnectedApp.setRemarks(remarks);

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<CCConnectedApp> data = service.findByCustomSearch(pageable, ccConnectedApp);
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

    @GetMapping("/search/cc.connectedApp/ccId/{id}")
    public ResponseEntity<Object> searchCCConnectedAppById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String connectedApp,
            @RequestParam(required = false) String subscription,
            @RequestParam(required = false) String autoDebit,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String remarks,
            HttpServletRequest httpServletRequest){

        String endPoint = httpServletRequest.getServerName() + URL + "/search/cc.connectedApp/ccId/" + id;
        try {
            tps.start(endPoint, " GET METHOD");
            log.debug("{} API - Start", endPoint);

            inputSanitation.validateNumeric(String.valueOf(id));
            inputSanitation.validateSortBy(sortBy);
            inputSanitation.sanitizeInput(sortBy);
            inputSanitation.validateSize(page);
            inputSanitation.validateSize(size);
            inputSanitation.sanitizeInput(connectedApp);
            inputSanitation.sanitizeInput(subscription);
            inputSanitation.sanitizeInput(autoDebit);
            inputSanitation.sanitizeInput(date);
            inputSanitation.sanitizeInput(remarks);

            List<CCConnectedApp> recordList = service.getCCConnectedAppByCCId(id);
            if (recordList.isEmpty()) {
                httpStatusReturn = String.valueOf(HttpStatus.BAD_REQUEST);
                httpStatusMsgReturn = Constant.GEN_ERR_MSG;
                response = Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
            } else {
                List<CCConnectedApp> filteredList = recordList.stream()
                    .filter(record -> connectedApp == null || record.getConnectedApp() != null && record.getConnectedApp().toLowerCase().contains(connectedApp.toLowerCase()))
                    .filter(record -> subscription == null || record.getSubscription() != null && record.getSubscription().toLowerCase().contains(subscription.toLowerCase()))
                    .filter(record -> autoDebit == null || record.getAutoDebit() != null && record.getAutoDebit().toLowerCase().contains(autoDebit.toLowerCase()))
                    .filter(record -> date == null || record.getDate() != null && record.getDate().toLowerCase().contains(date.toLowerCase()))
                    .filter(record -> remarks == null || record.getRemarks() != null && record.getRemarks().toLowerCase().contains(remarks.toLowerCase()))
                    .collect(Collectors.toList());

                Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
                Page<CCConnectedApp> dataPage = new PageImpl<>(filteredList, pageable, filteredList.size());
   
                httpStatusReturn = String.valueOf(HttpStatus.OK);
                httpStatusMsgReturn = Constant.SUCCESS;
                response = Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, dataPage);
            }
        } catch (BadCredentialsException | DataIntegrityViolationException | JwtException | IllegalArgumentException e){
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
            requestLogs.setRequestDetails("id: " + id);
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
