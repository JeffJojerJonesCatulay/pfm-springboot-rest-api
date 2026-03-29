package com.pfm.restapi.repository;

import com.pfm.restapi.entity.CCRecordExpenseTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CCRecordExpenseTrackerRepo extends JpaRepository<CCRecordExpenseTracker, Long> {
    @Query(value = "SELECT * FROM ccrecordexpensetracker", nativeQuery = true)
    Page<CCRecordExpenseTracker> getCCExpense(Pageable pageable);
    @Query(value = "SELECT * FROM ccrecordexpensetracker WHERE ccExpId = :id", nativeQuery = true)
    List<CCRecordExpenseTracker> getCCExpenseById(@Param("id") Long id);
    @Query(value = "SELECT * FROM ccrecordexpensetracker WHERE ccRecId = :ccRecId", nativeQuery = true)
    List<CCRecordExpenseTracker> getCCExpenseByccRecId(@Param("ccRecId") Long ccRecId);
}
