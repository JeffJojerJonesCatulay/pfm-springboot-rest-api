package com.pfm.restapi.repository;

import java.math.BigDecimal;
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
    List<InvestmentsAndSavingsDay> getInvestmentsAndSavingsDayById(@Param("id") Long id);
    @Query(value = "SELECT * FROM investmentsandsavingsday WHERE allocId = :id", nativeQuery = true)
    List<InvestmentsAndSavingsDay> getInvestmentsAndSavingsDayByAllocId(@Param("id") Long id);

    @Query(value = "SELECT SUM(valueAdded) " +
            "FROM investmentsandsavingsday " +
            "WHERE (:month IS NULL OR MONTHNAME(date) = :month) " +
            "AND (:year IS NULL OR YEAR(date) = :year) " +
            "AND (allocId = :allocId)",
            nativeQuery = true)
    Double sumValueAddedByMonthAndYear(
            @Param("month") String month,
            @Param("year") Long year,
            @Param("allocId") Long allocId);

    @Query(value = "SELECT SUM(valueAdded) " +
            "FROM investmentsandsavingsday " +
            "WHERE (:year IS NULL OR YEAR(date) = :year) " +
            "AND (allocId = :allocId)",
            nativeQuery = true)
    Double sumValueAddedByYear(
            @Param("year") Long year,
            @Param("allocId") Long allocId);

    @Query(value = "SELECT SUM(valueAdded) " +
            "FROM investmentsandsavingsday " +
            "WHERE (:year IS NULL OR YEAR(date) != :year) " +
            "AND (allocId = :allocId)",
            nativeQuery = true)
    Double sumValueAddedByPreviousYear(
            @Param("year") Long year,
            @Param("allocId") Long allocId);

    @Query(value = "SELECT marketValue " +
            "FROM investmentsandsavingsday " +
            "WHERE (:month IS NULL OR MONTHNAME(date) = :month) " +
            "AND (:year IS NULL OR YEAR(date) = :year)" +
            "AND (allocId = :allocId) ORDER BY DATE ASC, ID DESC LIMIT 1 ",
            nativeQuery = true)
    Double getCurrentMarketValue(
            @Param("month") String month,
            @Param("year") Long year,
            @Param("allocId") Long allocId)
            ;
}
