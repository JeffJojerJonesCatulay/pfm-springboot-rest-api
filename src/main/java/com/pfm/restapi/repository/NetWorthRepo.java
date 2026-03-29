package com.pfm.restapi.repository;

import com.pfm.restapi.entity.NetWorth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetWorthRepo extends JpaRepository<NetWorth, Long> {
    @Query(value = "SELECT * FROM networth", nativeQuery = true)
    Page<NetWorth> getNetWorth(Pageable pageable);

    @Query(value = "SELECT * FROM networth WHERE id = :id", nativeQuery = true)
    List<NetWorth> getNetWorthByAllocId(@Param("id") Long id);

    @Query(value = "SELECT * FROM networth WHERE month = :month and year = :year", nativeQuery = true)
    List<NetWorth> getNetWorthByMonthYear(@Param("month") String month, @Param("year") Long year);
}
