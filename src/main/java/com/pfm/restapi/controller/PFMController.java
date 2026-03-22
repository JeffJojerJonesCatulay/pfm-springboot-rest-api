package com.pfm.restapi.controller;

import com.pfm.restapi.entity.UserEntity;
import com.pfm.restapi.security.AuthRequest;
import com.pfm.restapi.security.AuthResponse;
import com.pfm.restapi.responseHandler.Response;
import com.pfm.restapi.security.JwtService;
import com.pfm.restapi.service.AllocationMappingService;
import com.pfm.restapi.entity.AllocationMapping;
import com.pfm.restapi.service.UserService;
import com.pfm.restapi.utility.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.url.mapping}")
public class PFMController  {


}
