package com.pfm.restapi.controller;

import com.pfm.restapi.entity.AllocationMapping;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.service.AllocationMappingService;
import com.pfm.restapi.utility.Constant;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/get/allocation.mapping/allocId/{id}")
    public ResponseEntity<Object> getAllocationMappingById(@PathVariable Long id){
        try{
            List<AllocationMapping> data = allocationMappingService.getAllocationMappingById(id);
            return Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, data);
        } catch (BadCredentialsException | DataIntegrityViolationException | JwtException e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/allocation.mapping/create/")
    public ResponseEntity<Object> createAllocation(@RequestBody AllocationMapping allocationMapping){
        try{
            AllocationMapping response = allocationMappingService.createAllocation(allocationMapping);
            return Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, response);
        } catch (BadCredentialsException | DataIntegrityViolationException e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/allocation.mapping/update/{id}")
    public ResponseEntity<Object> updateAllocation(@PathVariable Long id, @RequestBody AllocationMapping allocationMapping){
        try{
            Optional<AllocationMapping> existingAlloc = allocationMappingService.findById(id);
            if (existingAlloc.isEmpty()) {
                return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
            } else {
                AllocationMapping response = allocationMappingService.updateAllocation(allocationMapping, id);
                return Response.generateResponse(Constant.SUCCESS, HttpStatus.OK, response);
            }
        } catch (BadCredentialsException | DataIntegrityViolationException e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST, null);
        } catch (Exception e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/allocation.mapping/delete/{id}")
    public ResponseEntity<Object> updateAllocation(@PathVariable Long id){
        try{
            Optional<AllocationMapping> existingAlloc = allocationMappingService.findById(id);
            if (existingAlloc.isEmpty()) {
                return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST);
            } else {
                allocationMappingService.deleteAllocation(id);
                return Response.generateResponse(Constant.SUCCESS, HttpStatus.OK);
            }
        } catch (BadCredentialsException | DataIntegrityViolationException e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return Response.generateResponse(Constant.GEN_ERR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
