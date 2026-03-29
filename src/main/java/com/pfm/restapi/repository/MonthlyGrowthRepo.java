package com.pfm.restapi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfm.restapi.entity.MonthlyGrowth;

@Repository
public interface MonthlyGrowthRepo extends JpaRepository<MonthlyGrowth, Long> {
    @Query(value = "SELECT * FROM monthlygrowth", nativeQuery = true)
    Page<MonthlyGrowth> getMonthlyGrowth(Pageable pageable);
    @Query(value = "SELECT * FROM monthlygrowth WHERE id = :id", nativeQuery = true)
    List<MonthlyGrowth> getMonthlyGrowthById(@Param("id") Long id);
    @Query(value = "SELECT * FROM monthlygrowth WHERE allocId = :id", nativeQuery = true)
    List<MonthlyGrowth> getMonthlyGrowthByAllocId(@Param("id") Long id);
}
