package com.pfm.restapi.service;

import com.pfm.restapi.entity.SalaryExpenseTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SalaryExpenseTrackerService {
    Page<SalaryExpenseTracker> getSalaryExpenseTracker(Pageable pageable);

    List<SalaryExpenseTracker> getSalaryExpenseTrackerById(Long id);

    SalaryExpenseTracker createSalaryExpenseTracker(SalaryExpenseTracker salaryExpenseTracker);

    SalaryExpenseTracker updateSalaryExpenseTracker(SalaryExpenseTracker salaryExpenseTracker, Long id);

    Optional<SalaryExpenseTracker> findById(Long id);

    void deleteSalaryExpenseTracker(Long id);

    Page<SalaryExpenseTracker> findByCustomSearch(Pageable pageable, SalaryExpenseTracker salaryExpenseTracker);
}
