package com.pfm.restapi.service;

import com.pfm.restapi.entity.CCDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CCDetailsService {
    Page<CCDetails> getCCDetails(Pageable pageable);
    List<CCDetails> getCCDetailsById(Long id);
    CCDetails createCCDetails(CCDetails ccDetails);
    Optional<CCDetails> findById(Long id);
    CCDetails updateCCDetails(CCDetails ccDetails, Long id);
    void deleteCCDetails(Long id);
}
