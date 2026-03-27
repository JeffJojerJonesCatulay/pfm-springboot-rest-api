package com.pfm.restapi.repository;

import com.pfm.restapi.entity.CCDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CCDetailsRepo extends JpaRepository<CCDetails, Long> {
    @Query(value = "SELECT * FROM ccdetails", nativeQuery = true)
    Page<CCDetails> getCCDetails(Pageable pageable);
    @Query(value = "SELECT * FROM ccdetails WHERE ccId = :id", nativeQuery = true)
    List<CCDetails> getCCDetailsById(@Param("id") Long id);
}
