package com.pfm.restapi.service.impl;

import com.pfm.restapi.repository.AllocationMappingRepo;
import com.pfm.restapi.service.AllocationMappingService;
import com.pfm.restapi.entity.AllocationMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AllocationMappingServiceImpl implements AllocationMappingService {

    @Autowired
    private AllocationMappingRepo allocationMappingRepo;

    @Override
    public List<AllocationMapping> getAllocationMapping(){
        return allocationMappingRepo.getAllocationMapping();
    }

    @Override
    public List<AllocationMapping> getAllocationMappingById(Long id) {
        return allocationMappingRepo.getAllocationMappingById(id);
    }

    @Override
    public AllocationMapping createAllocation(AllocationMapping allocationMapping) {
        AllocationMapping data = new AllocationMapping();
        data.setAllocation(allocationMapping.getAllocation());
        data.setType(allocationMapping.getType());
        data.setDescription(allocationMapping.getDescription());
        data.setStatus(allocationMapping.getStatus());
        data.setAddedBy(allocationMapping.getAddedBy());
        data.setDateAdded(String.valueOf(LocalDateTime.now()));

        return allocationMappingRepo.save(data);
    }

    @Override
    public AllocationMapping updateAllocation(AllocationMapping allocationMapping, Long id) {
        List<AllocationMapping> allocationMappingId = getAllocationMappingById(id);
        AllocationMapping data = new AllocationMapping();
        data.setAllocId(id);
        data.setAllocation(allocationMapping.getAllocation() != null ? allocationMapping.getAllocation() : allocationMappingId.getFirst().getAllocation());
        data.setType(allocationMapping.getType() != null ? allocationMapping.getType() : allocationMappingId.getFirst().getType());
        data.setDescription(allocationMapping.getDescription() != null ? allocationMapping.getDescription() : allocationMappingId.getFirst().getDescription());
        data.setStatus(allocationMapping.getStatus() != null ? allocationMapping.getStatus() : allocationMappingId.getFirst().getStatus());
        data.setStatus(allocationMapping.getStatus() != null ? allocationMapping.getStatus() : allocationMappingId.getFirst().getStatus());
        data.setAddedBy(allocationMappingId.getFirst().getAddedBy());
        data.setDateAdded(allocationMappingId.getFirst().getDateAdded());
        data.setUpdateBy(allocationMapping.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));

        return allocationMappingRepo.save(data);
    }

    @Override
    public void deleteAllocation(Long id) {
        allocationMappingRepo.deleteById(id);
    }

    @Override
    public Optional<AllocationMapping> findById(Long id) {
        return allocationMappingRepo.findById(id);
    }
}
