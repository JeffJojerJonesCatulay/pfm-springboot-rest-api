package com.pfm.restapi.repository;

import com.pfm.restapi.entity.RequestLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogsRepo extends JpaRepository<RequestLogs, Long> {
}
