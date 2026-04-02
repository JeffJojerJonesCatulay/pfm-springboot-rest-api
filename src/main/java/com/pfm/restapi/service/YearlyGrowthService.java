package com.pfm.restapi.service;

import com.pfm.restapi.entity.InvestmentsAndSavingsDay;
import com.pfm.restapi.entity.YearlyGrowth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface YearlyGrowthService {
    Page<YearlyGrowth> getYearlyGrowth(Pageable pageable);
    List<YearlyGrowth> getYearlyGrowthById(Long id);
    List<YearlyGrowth> getYearlyGrowthByAllocId(Long id);
    YearlyGrowth createYearlyGrowth(YearlyGrowth yearlyGrowth);
    YearlyGrowth updateYearlyGrowth(YearlyGrowth yearlyGrowth, Long id);
    Optional<YearlyGrowth> findById(Long id);
    void deleteYearlyGrowth(Long id);
    void updateYearlyGrowthFromInvestmentsDay(Long id, Long year, InvestmentsAndSavingsDay investmentsAndSavingsDay);
}

