package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.YearlyGrowth;
import com.pfm.restapi.repository.YearlyGrowthRepo;
import com.pfm.restapi.service.YearlyGrowthService;
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
public class YearlyGrowthServiceImpl implements YearlyGrowthService {

    private static final Logger log = LoggerFactory.getLogger(YearlyGrowthServiceImpl.class);

    @Autowired
    private YearlyGrowthRepo repo;

    @Override
    public Page<YearlyGrowth> getYearlyGrowth(Pageable pageable) {
        log.debug("Inside getYearlyGrowth");
        return repo.getYearlyGrowth(pageable);
    }

    @Override
    public List<YearlyGrowth> getYearlyGrowthById(Long id) {
        log.debug("Inside getYearlyGrowthById");
        return repo.getYearlyGrowthId(id);
    }

    @Override
    public List<YearlyGrowth> getYearlyGrowthByAllocId(Long id) {
        log.debug("Inside findById {}", id);
        return repo.getYearlyGrowthAllocId(id);
    }

    @Override
    public YearlyGrowth createYearlyGrowth(YearlyGrowth yearlyGrowth) {
        log.debug("Inside createYearlyGrowth");
        yearlyGrowth.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", yearlyGrowth.toString());
        return repo.save(yearlyGrowth);
    }

    @Override
    public YearlyGrowth updateYearlyGrowth(YearlyGrowth yearlyGrowth, Long id) {
        log.debug("Inside updateYearly");
        List<YearlyGrowth> yearlyGrowthExisting = getYearlyGrowthById(id);
        log.debug("Existing Yearly: {}", yearlyGrowthExisting.toString());
        YearlyGrowth data = new YearlyGrowth();
        data.setId(id);
        data.setAllocId(yearlyGrowth.getAllocId() != 0 ? yearlyGrowth.getAllocId() : yearlyGrowthExisting.getFirst().getAllocId());
        data.setYear(yearlyGrowth.getYear() != 0 ? yearlyGrowth.getYear() : yearlyGrowthExisting.getFirst().getYear());
        data.setTotalContribution(yearlyGrowth.getTotalContribution() != 0 ? yearlyGrowth.getTotalContribution() : yearlyGrowthExisting.getFirst().getTotalContribution());
        data.setAverageContribution(yearlyGrowth.getAverageContribution() != 0 ? yearlyGrowth.getAverageContribution() : yearlyGrowthExisting.getFirst().getAverageContribution());
        data.setAverageCurrentValue(yearlyGrowth.getAverageCurrentValue() != 0 ? yearlyGrowth.getAverageCurrentValue() : yearlyGrowthExisting.getFirst().getAverageCurrentValue());
        data.setAverageGrowthRate(yearlyGrowth.getAverageGrowthRate() != 0 ? yearlyGrowth.getAverageGrowthRate() : yearlyGrowthExisting.getFirst().getAverageGrowthRate());
        data.setAddedBy(yearlyGrowthExisting.getFirst().getAddedBy());
        data.setDateAdded(yearlyGrowthExisting.getFirst().getDateAdded());
        data.setUpdateBy(yearlyGrowth.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated wantList: {}", data.toString());
        return repo.save(data);
    }

    @Override
    public Optional<YearlyGrowth> findById(Long id) {
        log.debug("Inside findById {}", id);
        return repo.findById(id);
    }

    @Override
    public void deleteYearlyGrowth(Long id) {
        log.debug("Inside deleteYearlyGrowth");
        repo.deleteById(id);
    }
}
