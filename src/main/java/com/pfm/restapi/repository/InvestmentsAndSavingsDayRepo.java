package com.pfm.restapi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfm.restapi.entity.InvestmentsAndSavingsDay;

@Repository
public interface InvestmentsAndSavingsDayRepo extends JpaRepository<InvestmentsAndSavingsDay, Long> {
    @Query(value = "SELECT * FROM investmentsandsavingsday", nativeQuery = true)
    Page<InvestmentsAndSavingsDay> getInvestmentsAndSavingsDay(Pageable pageable);

    @Query(value = "SELECT * FROM investmentsandsavingsday WHERE id = :id", nativeQuery = true)
    List<InvestmentsAndSavingsDay> getInvestmentsAndSavingsDayByAllocId(@Param("id") Long id);
}
