package com.pfm.restapi.repository;

import com.pfm.restapi.entity.CCConnectedApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CCConnectedAppRepo extends JpaRepository<CCConnectedApp, Long> {
    @Query(value = "SELECT * FROM ccconnectedapp", nativeQuery = true)
    Page<CCConnectedApp> getConnectedApp(Pageable pageable);
    @Query(value = "SELECT * FROM ccconnectedapp WHERE id = :id", nativeQuery = true)
    List<CCConnectedApp> getConnectedAppById(@Param("id") Long id);
}
