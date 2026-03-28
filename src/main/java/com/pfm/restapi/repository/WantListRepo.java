package com.pfm.restapi.repository;

import com.pfm.restapi.entity.WantList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WantListRepo extends JpaRepository<WantList, Long> {
    @Query(value = "SELECT * FROM wantlist", nativeQuery = true)
    Page<WantList> getWantList(Pageable pageable);

    @Query(value = "SELECT * FROM wantlist WHERE id = :id", nativeQuery = true)
    List<WantList> getWantListById(@Param("id") Long id);
}
