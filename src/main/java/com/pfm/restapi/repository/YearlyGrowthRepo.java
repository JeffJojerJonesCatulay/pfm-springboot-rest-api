package com.pfm.restapi.repository;

import com.pfm.restapi.entity.YearlyGrowth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearlyGrowthRepo extends JpaRepository<YearlyGrowth, Long> {
    @Query(value = "SELECT * FROM yearlygrowth", nativeQuery = true)
    Page<YearlyGrowth> getYearlyGrowth(Pageable pageable);
    @Query(value = "SELECT * FROM yearlygrowth WHERE id = :id", nativeQuery = true)
    List<YearlyGrowth> getYearlyGrowthId(@Param("id") Long id);
    @Query(value = "SELECT * FROM yearlygrowth WHERE allocId = :id", nativeQuery = true)
    List<YearlyGrowth> getYearlyGrowthAllocId(@Param("id") Long id);
    @Query(value = "SELECT * FROM yearlygrowth WHERE allocId = :id and year = :year", nativeQuery = true)
    YearlyGrowth getExistingId(@Param("id") Long id, @Param("year") Long year);
}
