package com.pfm.restapi.service;

import com.pfm.restapi.entity.CCRecordTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CCRecordTrackerService {
    Page<CCRecordTracker> getCCRecordTracker(Pageable pageable);
    List<CCRecordTracker> getCCRecordTrackerById(Long id);
    CCRecordTracker createCCRecordTracker(CCRecordTracker ccRecordTracker);
    Optional<CCRecordTracker> findById(Long id);
    CCRecordTracker updateCCRecordTracker(CCRecordTracker ccRecordTracker, Long id);
    void deleteCCRecordTracker(Long id);
}
