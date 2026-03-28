package com.pfm.restapi.service;

import com.pfm.restapi.entity.NetWorth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NetWorthService {
    Page<NetWorth> getNetWorth(Pageable pageable);

    List<NetWorth> getNetWorthByAllocId(Long id);

    NetWorth createNetWorth(NetWorth netWorth);

    NetWorth updateNetWorth(NetWorth netWorth, Long id);

    Optional<NetWorth> findById(Long id);

    void deleteNetWorth(Long id);
}
