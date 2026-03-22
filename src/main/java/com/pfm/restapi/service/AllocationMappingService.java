package com.pfm.restapi.service;

import com.pfm.restapi.entity.AllocationMapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AllocationMappingService {
    List<AllocationMapping> getAllocationMapping();
    List<AllocationMapping> getAllocationMappingById(Long id);
    AllocationMapping createAllocation(AllocationMapping allocationMapping);
    AllocationMapping updateAllocation(AllocationMapping allocationMapping, Long id);
    Optional<AllocationMapping> findById(Long id);
    void deleteAllocation(Long id);
}
