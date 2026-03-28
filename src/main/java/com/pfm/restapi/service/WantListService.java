package com.pfm.restapi.service;

import com.pfm.restapi.entity.WantList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface WantListService {
    Page<WantList> getWantList(Pageable pageable);

    List<WantList> getWantListById(Long id);

    WantList createWantList(WantList wantList);

    WantList updateWantList(WantList wantList, Long id);

    Optional<WantList> findById(Long id);

    void deleteWantList(Long id);
}
