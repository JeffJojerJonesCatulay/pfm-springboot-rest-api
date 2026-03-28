package com.pfm.restapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pfm.restapi.entity.InvestmentsAndSavingsDay;
import com.pfm.restapi.repository.InvestmentsAndSavingsDayRepo;
import com.pfm.restapi.service.InvestmentsAndSavingsDayService;

@Service
public class InvestmentsAndSavingsDayServiceImpl implements InvestmentsAndSavingsDayService {

    private static final Logger log = LoggerFactory.getLogger(InvestmentsAndSavingsDayServiceImpl.class);

    @Autowired
    private InvestmentsAndSavingsDayRepo investmentsAndSavingsDayRepo;

    @Override
    public Page<InvestmentsAndSavingsDay> getInvestmentsAndSavingsDay(Pageable pageable) {
        log.debug("Inside getInvestmentsAndSavingsDay");
        return investmentsAndSavingsDayRepo.getInvestmentsAndSavingsDay(pageable);
    }

    @Override
    public List<InvestmentsAndSavingsDay> getInvestmentsAndSavingsDayByAllocId(Long id) {
        log.debug("Inside getInvestmentsAndSavingsDayByAllocId");
        return investmentsAndSavingsDayRepo.getInvestmentsAndSavingsDayByAllocId(id);
    }

    @Override
    public InvestmentsAndSavingsDay createInvestmentsAndSavingsDay(InvestmentsAndSavingsDay investmentsAndSavingsDay) {
        log.debug("Inside createInvestmentsAndSavingsDay");
        investmentsAndSavingsDay.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", investmentsAndSavingsDay.toString());
        return investmentsAndSavingsDayRepo.save(investmentsAndSavingsDay);
    }

    @Override
    public InvestmentsAndSavingsDay updateInvestmentsAndSavingsDay(InvestmentsAndSavingsDay investmentsAndSavingsDay, Long id) {
        log.debug("Inside updateInvestmentsAndSavingsDay");
        List<InvestmentsAndSavingsDay> ccRecordTrackersExisting = getInvestmentsAndSavingsDayByAllocId(id);
        log.debug("Existing ccRecordTrackers: {}", ccRecordTrackersExisting.toString());
        InvestmentsAndSavingsDay data = new InvestmentsAndSavingsDay();
        data.setId(id);
        data.setAllocId(investmentsAndSavingsDay.getAllocId() != 0 ? investmentsAndSavingsDay.getAllocId() : ccRecordTrackersExisting.getFirst().getAllocId());
        data.setDate(investmentsAndSavingsDay.getDate() != null ? investmentsAndSavingsDay.getDate() : ccRecordTrackersExisting.getFirst().getDate());
        data.setValueAdded(investmentsAndSavingsDay.getValueAdded() != 0 ? investmentsAndSavingsDay.getValueAdded() : ccRecordTrackersExisting.getFirst().getValueAdded());
        data.setMarketValue(investmentsAndSavingsDay.getMarketValue() != 0 ? investmentsAndSavingsDay.getMarketValue() : ccRecordTrackersExisting.getFirst().getMarketValue());
        data.setAddedBy(ccRecordTrackersExisting.getFirst().getAddedBy());
        data.setDateAdded(ccRecordTrackersExisting.getFirst().getDateAdded());
        data.setUpdateBy(investmentsAndSavingsDay.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated investmentsAndSavingsDay: {}", data.toString());
        return investmentsAndSavingsDayRepo.save(data);
    }

    @Override
    public void deleteInvestmentsAndSavingsDay(Long id) {
        log.debug("Inside deleteInvestmentsAndSavingsDay");
        investmentsAndSavingsDayRepo.deleteById(id);
    }

    @Override
    public Optional<InvestmentsAndSavingsDay> findById(Long id) {
        log.debug("Inside findById {}", id);
        return investmentsAndSavingsDayRepo.findById(id);
    }
}
