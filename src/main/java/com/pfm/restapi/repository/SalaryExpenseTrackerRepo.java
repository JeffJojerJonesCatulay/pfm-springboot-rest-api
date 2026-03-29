package com.pfm.restapi.repository;

import com.pfm.restapi.entity.SalaryExpenseTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryExpenseTrackerRepo extends JpaRepository<SalaryExpenseTracker, Long> {
    @Query(value = "SELECT * FROM salaryexpensetracker", nativeQuery = true)
    Page<SalaryExpenseTracker> getSalaryExpenseTracker(Pageable pageable);

    @Query(value = "SELECT * FROM salaryexpensetracker WHERE id = :id", nativeQuery = true)
    List<SalaryExpenseTracker> getSalaryExpenseTrackerById(@Param("id") Long id);

    @Query(value = "SELECT * FROM salaryexpensetracker WHERE salaryId = :salaryId", nativeQuery = true)
    List<SalaryExpenseTracker> getSalaryExpenseTrackerBySalaryId(@Param("salaryId") Long salaryId);
}
