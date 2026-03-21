package com.pfm.restapi.service.impl;

import com.pfm.restapi.repository.AllocationMappingRepo;
import com.pfm.restapi.service.AllocationMappingService;
import com.pfm.restapi.entity.AllocationMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllocationMappingServiceImpl implements AllocationMappingService {

    @Autowired
    private AllocationMappingRepo allocationMappingRepo;

    @Override
    public List<AllocationMapping> getAllocationMapping(){
        return allocationMappingRepo.getAllocationMapping();
    }

}
