package com.pfm.restapi.service;

import com.pfm.restapi.entity.CCRecordExpenseTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CCRecordExpenseTrackerService {
    Page<CCRecordExpenseTracker> getCCExpense(Pageable pageable);
    List<CCRecordExpenseTracker> getCCExpenseById(Long id);
    List<CCRecordExpenseTracker> getCCExpenseByccRecId(Long ccRecId);
    CCRecordExpenseTracker createCCExpense(CCRecordExpenseTracker ccRecordExpenseTracker);
    Optional<CCRecordExpenseTracker> findById(Long id);
    CCRecordExpenseTracker updateCCExpense(CCRecordExpenseTracker ccRecordExpenseTracker, Long id);
    void deleteCCExpense(Long id);
    Page<CCRecordExpenseTracker> findByCustomSearch(Pageable pageable, CCRecordExpenseTracker ccRecordExpenseTracker);
}
