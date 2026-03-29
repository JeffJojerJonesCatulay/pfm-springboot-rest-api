package com.pfm.restapi.service;

import com.pfm.restapi.entity.SalaryTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SalaryTrackerService {
    Page<SalaryTracker> getSalaryTracker(Pageable pageable);

    List<SalaryTracker> getSalaryTrackerBySalaryId(Long salaryId);

    SalaryTracker createSalaryTracker(SalaryTracker salaryTracker);

    SalaryTracker updateSalaryTracker(SalaryTracker salaryTracker, Long salaryId);

    Optional<SalaryTracker> findById(Long salaryId);

    void deleteSalaryTracker(Long salaryId);

    Page<SalaryTracker> findByCustomSearch(Pageable pageable, SalaryTracker salaryTracker);
}
