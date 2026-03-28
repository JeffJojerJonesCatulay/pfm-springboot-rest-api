package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.MonthlyGrowth;
import com.pfm.restapi.repository.MonthlyGrowthRepo;
import com.pfm.restapi.service.MonthlyGrowthService;
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
public class MonthlyGrowthServiceImpl implements MonthlyGrowthService {

    private static final Logger log = LoggerFactory.getLogger(MonthlyGrowthServiceImpl.class);

    @Autowired
    private MonthlyGrowthRepo monthlyGrowthRepo;

    @Override
    public Page<MonthlyGrowth> getMonthlyGrowth(Pageable pageable) {
        log.debug("Inside getMonthlyGrowth");
        return monthlyGrowthRepo.getMonthlyGrowth(pageable);
    }

    @Override
    public List<MonthlyGrowth> getMonthlyGrowthByAllocId(Long id) {
        log.debug("Inside getMonthlyGrowthByAllocId");
        return monthlyGrowthRepo.getMonthlyGrowthByAllocId(id);
    }

    @Override
    public MonthlyGrowth createMonthlyGrowth(MonthlyGrowth monthlyGrowth) {
        log.debug("Inside createMonthlyGrowth");
        monthlyGrowth.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", monthlyGrowth.toString());
        return monthlyGrowthRepo.save(monthlyGrowth);
    }

    @Override
    public MonthlyGrowth updateMonthlyGrowth(MonthlyGrowth monthlyGrowth, Long id) {
        log.debug("Inside updateMonthlyGrowth");
        List<MonthlyGrowth> monthlyGrowthExisting = getMonthlyGrowthByAllocId(id);
        log.debug("Existing monthlyGrowth: {}", monthlyGrowthExisting.toString());
        MonthlyGrowth data = new MonthlyGrowth();
        data.setId(id);
        data.setAllocId(monthlyGrowth.getAllocId() != 0 ? monthlyGrowth.getAllocId() : monthlyGrowthExisting.getFirst().getAllocId());
        data.setMonth(monthlyGrowth.getMonth() != null ? monthlyGrowth.getMonth() : monthlyGrowthExisting.getFirst().getMonth());
        data.setYear(monthlyGrowth.getYear() != 0 ? monthlyGrowth.getYear() : monthlyGrowthExisting.getFirst().getYear());
        data.setContribution(monthlyGrowth.getContribution() != 0 ? monthlyGrowth.getContribution() : monthlyGrowthExisting.getFirst().getContribution());
        data.setTotalContribution(monthlyGrowth.getTotalContribution() != 0 ? monthlyGrowth.getTotalContribution() : monthlyGrowthExisting.getFirst().getTotalContribution());
        data.setCurrentValue(monthlyGrowth.getCurrentValue() != 0 ? monthlyGrowth.getCurrentValue() : monthlyGrowthExisting.getFirst().getCurrentValue());
        data.setGrowthRate(monthlyGrowth.getGrowthRate() != 0 ? monthlyGrowth.getGrowthRate() : monthlyGrowthExisting.getFirst().getGrowthRate());
        data.setPreviousContrib(monthlyGrowth.getPreviousContrib() != 0 ? monthlyGrowth.getPreviousContrib() : monthlyGrowthExisting.getFirst().getPreviousContrib());
        data.setAddedBy(monthlyGrowthExisting.getFirst().getAddedBy());
        data.setDateAdded(monthlyGrowthExisting.getFirst().getDateAdded());
        data.setUpdateBy(monthlyGrowth.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated monthlyGrowth: {}", data.toString());
        return monthlyGrowthRepo.save(data);
    }

    @Override
    public void deleteMonthlyGrowth(Long id) {
        log.debug("Inside deleteMonthlyGrowth");
        monthlyGrowthRepo.deleteById(id);
    }

    @Override
    public Optional<MonthlyGrowth> findById(Long id) {
        log.debug("Inside findById {}", id);
        return monthlyGrowthRepo.findById(id);
    }
}
