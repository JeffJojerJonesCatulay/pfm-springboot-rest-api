package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.CCRecordExpenseTracker;
import com.pfm.restapi.repository.CCRecordExpenseTrackerRepo;
import com.pfm.restapi.service.CCRecordExpenseTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CCRecordExpenseTrackerServiceImpl implements CCRecordExpenseTrackerService {

    private static final Logger log = LoggerFactory.getLogger(CCRecordExpenseTrackerServiceImpl.class);

    @Autowired
    private CCRecordExpenseTrackerRepo repo;


    @Override
    public Page<CCRecordExpenseTracker> getCCExpense(Pageable pageable) {
        log.debug("Inside getCCExpense");
        return repo.getCCExpense(pageable);
    }

    @Override
    public List<CCRecordExpenseTracker> getCCExpenseById(Long id) {
        log.debug("Inside getCCExpenseById");
        return repo.getCCExpenseById(id);
    }

    @Override
    public CCRecordExpenseTracker createCCExpense(CCRecordExpenseTracker ccRecordExpenseTracker) {
        log.debug("Inside createCCDetails");
        ccRecordExpenseTracker.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", ccRecordExpenseTracker.toString());
        return repo.save(ccRecordExpenseTracker);
    }

    @Override
    public CCRecordExpenseTracker updateCCExpense(CCRecordExpenseTracker ccRecordExpenseTracker, Long id) {
        log.debug("Inside updateCCExpense");
        List<CCRecordExpenseTracker> ccRecordExpenseTrackers = getCCExpenseById(id);
        log.debug("Existing CCExpenseId: {}", ccRecordExpenseTrackers.toString());
        CCRecordExpenseTracker data = new CCRecordExpenseTracker();
        data.setCcExpId(id);
        data.setCcRecId(ccRecordExpenseTracker.getCcRecId() != 0 ? ccRecordExpenseTracker.getCcRecId() : ccRecordExpenseTrackers.getFirst().getCcRecId());
        data.setDate(ccRecordExpenseTracker.getDate() != null ? ccRecordExpenseTracker.getDate() : ccRecordExpenseTrackers.getFirst().getDate());
        data.setExpenseDescription(ccRecordExpenseTracker.getExpenseDescription() != null ? ccRecordExpenseTracker.getExpenseDescription() : ccRecordExpenseTrackers.getFirst().getExpenseDescription());
        data.setExpenseValue(ccRecordExpenseTracker.getExpenseValue() != 0 ? ccRecordExpenseTracker.getExpenseValue() : ccRecordExpenseTrackers.getFirst().getExpenseValue());
        data.setAddedBy(ccRecordExpenseTrackers.getFirst().getAddedBy());
        data.setDateAdded(ccRecordExpenseTrackers.getFirst().getDateAdded());
        data.setUpdatedBy(ccRecordExpenseTracker.getUpdatedBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated CCExpenseId: {}", data.toString());

        return repo.save(data);
    }

    @Override
    public void deleteCCExpense(Long id) {
        log.debug("Inside deleteCCExpense");
        repo.deleteById(id);
    }

    @Override
    public Optional<CCRecordExpenseTracker> findById(Long id) {
        log.debug("Inside findById " + id);
        return repo.findById(id);
    }
}
