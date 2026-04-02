package com.pfm.restapi.service;

import com.pfm.restapi.entity.InvestmentsAndSavingsDay;
import com.pfm.restapi.entity.MonthlyGrowth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MonthlyGrowthService {
    Page<MonthlyGrowth> getMonthlyGrowth(Pageable pageable);
    List<MonthlyGrowth> getMonthlyGrowthById(Long id);
    List<MonthlyGrowth> getMonthlyGrowthByAllocId(Long id);
    MonthlyGrowth createMonthlyGrowth(MonthlyGrowth monthlyGrowth);
    MonthlyGrowth updateMonthlyGrowth(MonthlyGrowth monthlyGrowth, Long id);
    Optional<MonthlyGrowth> findById(Long id);
    void deleteMonthlyGrowth(Long id);
    void updateMonthlyGrowthFromInvestmentsDay(Long id, String month, Long year, InvestmentsAndSavingsDay investmentsAndSavingsDay);
}
