package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.InvestmentsAndSavingsDay;
import com.pfm.restapi.entity.MonthlyGrowth;
import com.pfm.restapi.repository.InvestmentsAndSavingsDayRepo;
import com.pfm.restapi.repository.MonthlyGrowthRepo;
import com.pfm.restapi.service.InvestmentsAndSavingsDayService;
import com.pfm.restapi.service.MonthlyGrowthService;
import com.pfm.restapi.utility.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlyGrowthServiceImpl implements MonthlyGrowthService {

    private static final Logger log = LoggerFactory.getLogger(MonthlyGrowthServiceImpl.class);

    @Autowired
    private MonthlyGrowthRepo monthlyGrowthRepo;

    @Autowired
    private InvestmentsAndSavingsDayRepo investmentsAndSavingsDayRepo;

    @Override
    public Page<MonthlyGrowth> getMonthlyGrowth(Pageable pageable) {
        log.debug("Inside getMonthlyGrowth");
        return monthlyGrowthRepo.getMonthlyGrowth(pageable);
    }

    @Override
    public List<MonthlyGrowth> getMonthlyGrowthById(Long id) {
        log.debug("Inside getMonthlyGrowthByAllocId");
        return monthlyGrowthRepo.getMonthlyGrowthById(id);
    }

    @Override
    public List<MonthlyGrowth> getMonthlyGrowthByAllocId(Long id) {
        log.debug("Inside getMonthlyGrowthByAllocId {}", id);
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
        List<MonthlyGrowth> monthlyGrowthExisting = getMonthlyGrowthById(id);
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

    @Override
    public void updateMonthlyGrowthFromInvestmentsDay(Long id, String month, Long year, InvestmentsAndSavingsDay investmentsAndSavingsDay) {
        log.debug("Inside updateMonthlyGrowthFromInvestmentsDay");
        log.debug("id: {} month: {} year: {}", id, month, year);
        MonthlyGrowth existingId = monthlyGrowthRepo.getExistingId(id, month, year);
        MonthlyGrowth monthlyGrowth = new MonthlyGrowth();
        Double contributionCurrentMonthTemp = investmentsAndSavingsDayRepo.sumValueAddedByMonthAndYear(month, year, (long) investmentsAndSavingsDay.getAllocId());
        double contributionCurrentMonth = (contributionCurrentMonthTemp != null) ? contributionCurrentMonthTemp : 0.0;
        Double contributionCurrentYearTemp = investmentsAndSavingsDayRepo.sumValueAddedByYear(year, (long) investmentsAndSavingsDay.getAllocId());
        double contributionCurrentYear = (contributionCurrentYearTemp != null) ? contributionCurrentYearTemp : 0.0;
        Double contributionPreviousYearTemp = investmentsAndSavingsDayRepo.sumValueAddedByPreviousYear(year, (long) investmentsAndSavingsDay.getAllocId());
        double contributionPreviousYear = (contributionPreviousYearTemp != null) ? contributionPreviousYearTemp : 0.0;
        Double currentMarketValueTemp = investmentsAndSavingsDayRepo.getCurrentMarketValue(month, year, (long) investmentsAndSavingsDay.getAllocId());
        double currentMarketValue = (currentMarketValueTemp != null) ? currentMarketValueTemp : 0.0;
        double growthRate = new Global().computeGrowthRate(currentMarketValue, contributionCurrentYear + contributionPreviousYear);

        if (existingId == null) {
            monthlyGrowth.setAllocId(investmentsAndSavingsDay.getAllocId());
            monthlyGrowth.setDateAdded(String.valueOf(LocalDateTime.now()));
            monthlyGrowth.setAddedBy(investmentsAndSavingsDay.getAddedBy());
        } else {
            monthlyGrowth.setId(existingId.getId());
            monthlyGrowth.setAllocId(existingId.getAllocId());
            monthlyGrowth.setDateAdded(existingId.getDateAdded());
            monthlyGrowth.setAddedBy(existingId.getAddedBy());
            monthlyGrowth.setUpdateDate(String.valueOf(LocalDateTime.now()));
            monthlyGrowth.setUpdateBy(investmentsAndSavingsDay.getAddedBy());
        }

        monthlyGrowth.setMonth(month);
        monthlyGrowth.setYear(Math.toIntExact(year));
        monthlyGrowth.setContribution(contributionCurrentMonth);
        monthlyGrowth.setTotalContribution(contributionCurrentYear);
        monthlyGrowth.setCurrentValue(currentMarketValue);
        monthlyGrowth.setGrowthRate(growthRate);
        monthlyGrowth.setPreviousContrib(contributionPreviousYear);
        monthlyGrowthRepo.save(monthlyGrowth);
    }
}
