package com.pfm.restapi.controller;

import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.service.AllocationMappingService;
import com.pfm.restapi.entity.AllocationMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1.0.0/pfm")
public class PFMController {

    @Autowired
    private AllocationMappingService allocationMappingService;

    @GetMapping
    public ResponseEntity<Object> getAllocationMapping(){
        List<AllocationMapping> data = allocationMappingService.getAllocationMapping();
        return Response.generateResponse("Success", HttpStatus.OK, data);
    }
}
