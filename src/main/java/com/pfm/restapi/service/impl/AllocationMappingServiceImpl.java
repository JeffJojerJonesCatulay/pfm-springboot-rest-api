package com.pfm.restapi.service.impl;

import com.pfm.restapi.controller.AllocationMappingController;
import com.pfm.restapi.repository.AllocationMappingRepo;
import com.pfm.restapi.service.AllocationMappingService;
import com.pfm.restapi.entity.AllocationMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AllocationMappingServiceImpl implements AllocationMappingService {

    @Autowired
    private AllocationMappingRepo allocationMappingRepo;

    private static final Logger log = LoggerFactory.getLogger(AllocationMappingServiceImpl.class);

    @Override
    public Page<AllocationMapping> getAllocationMapping(Pageable pageable){
        log.debug("Inside getAllocationMapping");
        return allocationMappingRepo.getAllocationMapping(pageable);
    }

    @Override
    public List<AllocationMapping> getAllocationMappingById(Long id) {
        log.debug("Inside getAllocationMappingById");
        return allocationMappingRepo.getAllocationMappingById(id);
    }

    @Override
    public AllocationMapping createAllocation(AllocationMapping allocationMapping) {
        log.debug("Inside createAllocation");
        allocationMapping.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", allocationMapping.toString());

        return allocationMappingRepo.save(allocationMapping);
    }

    @Override
    public AllocationMapping updateAllocation(AllocationMapping allocationMapping, Long id) {
        log.debug("Inside updateAllocation");
        List<AllocationMapping> allocationMappingId = getAllocationMappingById(id);
        log.debug("Existing allocationMappingId: {}", allocationMappingId.toString());
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
        log.debug("Updated allocationMappingId: {}", data.toString());

        return allocationMappingRepo.save(data);
    }

    @Override
    public void deleteAllocation(Long id) {
        log.debug("Inside deleteAllocation");
        allocationMappingRepo.deleteById(id);
    }

    @Override
    public Page<AllocationMapping> findByCustomSearch(Pageable pageable, AllocationMapping allocationMapping) {
        log.debug("Inside findByCustomSearch");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("allocId", "dateAdded", "addedBy", "updateDate", "updateBy")
                .withMatcher("allocation", match -> match.contains().ignoreCase())
                .withMatcher("type", match -> match.contains().ignoreCase())
                .withMatcher("description", match -> match.contains().ignoreCase())
                .withMatcher("status", match -> match.contains().ignoreCase());

        Example<AllocationMapping> example = Example.of(allocationMapping, matcher);

        return allocationMappingRepo.findAll(example, pageable);
    }

    @Override
    public Optional<AllocationMapping> findById(Long id) {
        log.debug("Inside findById " + id);
        return allocationMappingRepo.findById(id);
    }
}
