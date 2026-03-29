package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.SalaryExpenseTracker;
import com.pfm.restapi.repository.SalaryExpenseTrackerRepo;
import com.pfm.restapi.service.SalaryExpenseTrackerService;
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
public class SalaryExpenseTrackerServiceImpl implements SalaryExpenseTrackerService {

    private static final Logger log = LoggerFactory.getLogger(SalaryExpenseTrackerServiceImpl.class);

    @Autowired
    private SalaryExpenseTrackerRepo salaryExpenseTrackerRepo;

    @Override
    public Page<SalaryExpenseTracker> getSalaryExpenseTracker(Pageable pageable) {
        log.debug("Inside getSalaryExpenseTracker");
        return salaryExpenseTrackerRepo.getSalaryExpenseTracker(pageable);
    }

    @Override
    public List<SalaryExpenseTracker> getSalaryExpenseTrackerById(Long id) {
        log.debug("Inside getSalaryExpenseTrackerById");
        return salaryExpenseTrackerRepo.getSalaryExpenseTrackerById(id);
    }

    @Override
    public List<SalaryExpenseTracker> getSalaryExpenseTrackerBySalaryId(Long salaryId) {
        log.debug("Inside getSalaryExpenseTrackerBySalaryId");
        return salaryExpenseTrackerRepo.getSalaryExpenseTrackerBySalaryId(salaryId);
    }

    @Override
    public SalaryExpenseTracker createSalaryExpenseTracker(SalaryExpenseTracker salaryExpenseTracker) {
        log.debug("Inside createSalaryExpenseTracker");
        salaryExpenseTracker.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", salaryExpenseTracker.toString());
        return salaryExpenseTrackerRepo.save(salaryExpenseTracker);
    }

    @Override
    public SalaryExpenseTracker updateSalaryExpenseTracker(SalaryExpenseTracker salaryExpenseTracker, Long id) {
        log.debug("Inside updateSalaryExpenseTracker");
        List<SalaryExpenseTracker> salaryExpenseTrackerExisting = getSalaryExpenseTrackerById(id);
        log.debug("Existing salaryExpenseTracker: {}", salaryExpenseTrackerExisting.toString());
        SalaryExpenseTracker data = new SalaryExpenseTracker();
        data.setId(id);
        data.setSalaryId(salaryExpenseTracker.getSalaryId() != 0 ? salaryExpenseTracker.getSalaryId() : salaryExpenseTrackerExisting.getFirst().getSalaryId());
        data.setDate(salaryExpenseTracker.getDate() != null ? salaryExpenseTracker.getDate() : salaryExpenseTrackerExisting.getFirst().getDate());
        data.setExpenseDescription(salaryExpenseTracker.getExpenseDescription() != null ? salaryExpenseTracker.getExpenseDescription() : salaryExpenseTrackerExisting.getFirst().getExpenseDescription());
        data.setExpenseValue(salaryExpenseTracker.getExpenseValue() != 0 ? salaryExpenseTracker.getExpenseValue() : salaryExpenseTrackerExisting.getFirst().getExpenseValue());
        data.setExpenseType(salaryExpenseTracker.getExpenseType() != null ? salaryExpenseTracker.getExpenseType() : salaryExpenseTrackerExisting.getFirst().getExpenseType());
        data.setAddedBy(salaryExpenseTrackerExisting.getFirst().getAddedBy());
        data.setDateAdded(salaryExpenseTrackerExisting.getFirst().getDateAdded());
        data.setUpdateBy(salaryExpenseTracker.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated salaryExpenseTracker: {}", data.toString());
        return salaryExpenseTrackerRepo.save(data);
    }

    @Override
    public void deleteSalaryExpenseTracker(Long id) {
        log.debug("Inside deleteSalaryExpenseTracker");
        salaryExpenseTrackerRepo.deleteById(id);
    }

    @Override
    public Optional<SalaryExpenseTracker> findById(Long id) {
        log.debug("Inside findById {}", id);
        return salaryExpenseTrackerRepo.findById(id);
    }

    @Override
    public Page<SalaryExpenseTracker> findByCustomSearch(Pageable pageable, SalaryExpenseTracker salaryExpenseTracker) {
        log.debug("Inside findByCustomSearch");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id", "salaryId", "date", "expenseValue", "dateAdded", "addedBy", "updateDate", "updateBy")
                .withMatcher("expenseDescription", match -> match.contains().ignoreCase())
                .withMatcher("expenseType", match -> match.contains().ignoreCase());

        Example<SalaryExpenseTracker> example = Example.of(salaryExpenseTracker, matcher);

        return salaryExpenseTrackerRepo.findAll(example, pageable);
    }

}
