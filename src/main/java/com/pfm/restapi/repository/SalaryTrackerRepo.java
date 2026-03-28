package com.pfm.restapi.repository;

import com.pfm.restapi.entity.SalaryTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryTrackerRepo extends JpaRepository<SalaryTracker, Long> {
    @Query(value = "SELECT * FROM salarytracker", nativeQuery = true)
    Page<SalaryTracker> getSalaryTracker(Pageable pageable);

    @Query(value = "SELECT * FROM salarytracker WHERE salaryId = :id", nativeQuery = true)
    List<SalaryTracker> getSalaryTrackerBySalaryId(@Param("id") Long id);
}
