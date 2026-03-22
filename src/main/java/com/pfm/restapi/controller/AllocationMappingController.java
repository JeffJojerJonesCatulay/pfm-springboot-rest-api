package com.pfm.restapi.controller;

import com.pfm.restapi.entity.AllocationMapping;
import com.pfm.restapi.entity.UserEntity;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.AuthResponse;
import com.pfm.restapi.service.AllocationMappingService;
import com.pfm.restapi.utility.Constant;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.url.mapping}")
public class AllocationMappingController {
    @Autowired
    private AllocationMappingService allocationMappingService;

    @GetMapping("/get/allocation.mapping/")
    public ResponseEntity<Object> getAllocationMapping(){
        try{
            List<AllocationMapping> data = allocationMappingService.getAllocationMapping();
            return Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, data);
        } catch (BadCredentialsException | DataIntegrityViolationException | JwtException e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
