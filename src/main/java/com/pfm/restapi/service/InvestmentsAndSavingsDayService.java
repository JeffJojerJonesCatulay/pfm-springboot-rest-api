package com.pfm.restapi.service;

import com.pfm.restapi.entity.InvestmentsAndSavingsDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InvestmentsAndSavingsDayService {
    Page<InvestmentsAndSavingsDay> getInvestmentsAndSavingsDay(Pageable pageable);

    List<InvestmentsAndSavingsDay> getInvestmentsAndSavingsDayByAllocId(Long id);

    InvestmentsAndSavingsDay createInvestmentsAndSavingsDay(InvestmentsAndSavingsDay investmentsAndSavingsDay);

    InvestmentsAndSavingsDay updateInvestmentsAndSavingsDay(InvestmentsAndSavingsDay investmentsAndSavingsDay, Long id);

    Optional<InvestmentsAndSavingsDay> findById(Long id);

    void deleteInvestmentsAndSavingsDay(Long id);
}
