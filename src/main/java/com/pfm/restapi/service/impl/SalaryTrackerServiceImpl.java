package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.SalaryTracker;
import com.pfm.restapi.repository.SalaryTrackerRepo;
import com.pfm.restapi.service.SalaryTrackerService;
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
public class SalaryTrackerServiceImpl implements SalaryTrackerService {

    private static final Logger log = LoggerFactory.getLogger(SalaryTrackerServiceImpl.class);

    @Autowired
    private SalaryTrackerRepo salaryTrackerRepo;

    @Override
    public Page<SalaryTracker> getSalaryTracker(Pageable pageable) {
        log.debug("Inside getSalaryTracker");
        return salaryTrackerRepo.getSalaryTracker(pageable);
    }

    @Override
    public List<SalaryTracker> getSalaryTrackerBySalaryId(Long salaryId) {
        log.debug("Inside getSalaryTrackerBySalaryId");
        return salaryTrackerRepo.getSalaryTrackerBySalaryId(salaryId);
    }

    @Override
    public SalaryTracker createSalaryTracker(SalaryTracker salaryTracker) {
        log.debug("Inside createSalaryTracker");
        salaryTracker.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", salaryTracker.toString());
        return salaryTrackerRepo.save(salaryTracker);
    }

    @Override
    public SalaryTracker updateSalaryTracker(SalaryTracker salaryTracker, Long salaryId) {
        log.debug("Inside updateSalaryTracker");
        List<SalaryTracker> salaryTrackerExisting = getSalaryTrackerBySalaryId(salaryId);
        log.debug("Existing salaryTracker: {}", salaryTrackerExisting.toString());
        SalaryTracker data = new SalaryTracker();
        data.setSalaryId(salaryId);
        data.setDate(salaryTracker.getDate() != null ? salaryTracker.getDate() : salaryTrackerExisting.getFirst().getDate());
        data.setSalary(salaryTracker.getSalary() != 0 ? salaryTracker.getSalary() : salaryTrackerExisting.getFirst().getSalary());
        data.setStatus(salaryTracker.getStatus() != null ? salaryTracker.getStatus() : salaryTrackerExisting.getFirst().getStatus());
        data.setAddedBy(salaryTrackerExisting.getFirst().getAddedBy());
        data.setDateAdded(salaryTrackerExisting.getFirst().getDateAdded());
        data.setUpdateBy(salaryTracker.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated salaryTracker: {}", data.toString());
        return salaryTrackerRepo.save(data);
    }

    @Override
    public void deleteSalaryTracker(Long salaryId) {
        log.debug("Inside deleteSalaryTracker");
        salaryTrackerRepo.deleteById(salaryId);
    }

    @Override
    public Optional<SalaryTracker> findById(Long salaryId) {
        log.debug("Inside findById {}", salaryId);
        return salaryTrackerRepo.findById(salaryId);
    }

    @Override
    public Page<SalaryTracker> findByCustomSearch(Pageable pageable, SalaryTracker salaryTracker) {
        log.debug("Inside findByCustomSearch");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("salaryId", "date", "salary", "dateAdded", "addedBy", "updateDate", "updateBy")
                .withMatcher("status", match -> match.contains().ignoreCase());

        Example<SalaryTracker> example = Example.of(salaryTracker, matcher);

        return salaryTrackerRepo.findAll(example, pageable);
    }

}
