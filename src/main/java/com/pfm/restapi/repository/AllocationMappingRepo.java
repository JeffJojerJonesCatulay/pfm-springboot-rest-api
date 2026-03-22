package com.pfm.restapi.repository;

import com.pfm.restapi.entity.AllocationMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationMappingRepo extends JpaRepository<AllocationMapping, Long> {
    @Query(value = "SELECT * FROM allocationmapping", nativeQuery = true)
    List<AllocationMapping> getAllocationMapping();
    @Query(value = "SELECT * FROM allocationmapping WHERE allocId = :id", nativeQuery = true)
    List<AllocationMapping> getAllocationMappingById(@Param("id") Long id);
}
