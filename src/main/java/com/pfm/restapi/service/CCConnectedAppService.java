package com.pfm.restapi.service;

import com.pfm.restapi.entity.CCConnectedApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CCConnectedAppService {
    Page<CCConnectedApp> getCCConnectedApp(Pageable pageable);
    List<CCConnectedApp> getCCConnectedAppById(Long id);
    CCConnectedApp createConnectedApp(CCConnectedApp ccConnectedApp);
    CCConnectedApp updateConnectedApp(CCConnectedApp ccConnectedApp, Long id);
    Optional<CCConnectedApp> findById(Long id);
    void deleteAllocation(Long id);
    Page<CCConnectedApp> findByCustomSearch(Pageable pageable, CCConnectedApp ccConnectedApp);
    List<CCConnectedApp> getCCConnectedAppByCCId(Long id);
}
