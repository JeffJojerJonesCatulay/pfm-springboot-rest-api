package com.pfm.restapi.repository;

import com.pfm.restapi.entity.CCRecordTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CCRecordTrackerRepo extends JpaRepository<CCRecordTracker, Long> {
    @Query(value = "SELECT * FROM ccrecordtracker", nativeQuery = true)
    Page<CCRecordTracker> getCCRecord(Pageable pageable);
    @Query(value = "SELECT * FROM ccrecordtracker WHERE ccRecId = :id", nativeQuery = true)
    List<CCRecordTracker> getCCRecordById(@Param("id") Long id);
    @Query(value = "SELECT * FROM ccrecordtracker WHERE ccId = :id", nativeQuery = true)
    List<CCRecordTracker> getCCRecordTrackerByCCId(@Param("id") Long id);
}
