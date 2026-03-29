package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.CCRecordTracker;
import com.pfm.restapi.repository.CCRecordTrackerRepo;
import com.pfm.restapi.service.CCRecordTrackerService;
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
public class CCRecordTrackerServiceImpl implements CCRecordTrackerService {

    private static final Logger log = LoggerFactory.getLogger(CCRecordTrackerServiceImpl.class);

    @Autowired
    private CCRecordTrackerRepo repo;

    @Override
    public Page<CCRecordTracker> getCCRecordTracker(Pageable pageable) {
        log.debug("Inside getCCRecordTracker");
        return repo.getCCRecord(pageable);
    }

    @Override
    public List<CCRecordTracker> getCCRecordTrackerById(Long id) {
        log.debug("Inside getCCRecordTrackerById");
        return repo.getCCRecordById(id);
    }

    @Override
    public CCRecordTracker createCCRecordTracker(CCRecordTracker ccRecordTracker) {
        log.debug("Inside createCCDetails");
        ccRecordTracker.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", ccRecordTracker.toString());
        return repo.save(ccRecordTracker);
    }

    @Override
    public CCRecordTracker updateCCRecordTracker(CCRecordTracker ccRecordTracker, Long id) {
        log.debug("Inside updateCCRecordTracker");
        List<CCRecordTracker> ccRecordTrackers = getCCRecordTrackerById(id);
        log.debug("Existing CCRecordTrackerId: {}", ccRecordTrackers.toString());
        CCRecordTracker data = new CCRecordTracker();
        data.setCcRecId(id);
        data.setCcId(ccRecordTracker.getCcId() != 0 ? ccRecordTracker.getCcId() : ccRecordTrackers.getFirst().getCcId());
        data.setDateFrom(ccRecordTracker.getDateFrom() != null ? ccRecordTracker.getDateFrom() : ccRecordTrackers.getFirst().getDateFrom());
        data.setDateTo(ccRecordTracker.getDateTo() != null ? ccRecordTracker.getDateTo() : ccRecordTrackers.getFirst().getDateTo());
        data.setDueDate(ccRecordTracker.getDueDate() != null ? ccRecordTracker.getDueDate() : ccRecordTrackers.getFirst().getDueDate());
        data.setStatus(ccRecordTracker.getStatus() != null ? ccRecordTracker.getStatus() : ccRecordTrackers.getFirst().getStatus());
        data.setAddedBy(ccRecordTrackers.getFirst().getAddedBy());
        data.setDateAdded(ccRecordTrackers.getFirst().getDateAdded());
        data.setUpdatedBy(ccRecordTracker.getUpdatedBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated CCRecordTracker: {}", data.toString());

        return repo.save(data);
    }

    @Override
    public void deleteCCRecordTracker(Long id) {
        log.debug("Inside deleteCCRecordTracker");
        repo.deleteById(id);
    }

    @Override
    public Optional<CCRecordTracker> findById(Long id) {
        log.debug("Inside findById " + id);
        return repo.findById(id);
    }

    @Override
    public Page<CCRecordTracker> findByCustomSearch(Pageable pageable, CCRecordTracker ccRecordTracker) {
        log.debug("Inside findByCustomSearch");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("ccRecId", "ccId", "dateAdded", "addedBy", "updateDate", "updatedBy", "dateFrom", "dateTo", "dueDate")
                .withMatcher("status", match -> match.contains().ignoreCase());

        Example<CCRecordTracker> example = Example.of(ccRecordTracker, matcher);

        return repo.findAll(example, pageable);
    }

}
