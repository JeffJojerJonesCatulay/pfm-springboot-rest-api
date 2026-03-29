package com.pfm.restapi.service;

import com.pfm.restapi.entity.AllocationMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AllocationMappingService {
    Page<AllocationMapping> getAllocationMapping(Pageable pageable);
    List<AllocationMapping> getAllocationMappingById(Long id);
    AllocationMapping createAllocation(AllocationMapping allocationMapping);
    AllocationMapping updateAllocation(AllocationMapping allocationMapping, Long id);
    Optional<AllocationMapping> findById(Long id);
    void deleteAllocation(Long id);
    Page<AllocationMapping> findByCustomSearch(Pageable pageable, AllocationMapping allocationMapping);
}
