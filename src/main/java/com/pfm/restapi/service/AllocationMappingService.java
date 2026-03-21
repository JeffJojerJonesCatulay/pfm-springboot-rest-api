package com.pfm.restapi.service;

import com.pfm.restapi.entity.AllocationMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AllocationMappingService {
    List<AllocationMapping> getAllocationMapping();
}
