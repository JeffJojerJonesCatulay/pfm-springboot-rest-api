package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.CCConnectedApp;
import com.pfm.restapi.repository.CCConnectedAppRepo;
import com.pfm.restapi.service.CCConnectedAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CCConnectedAppServiceImpl implements CCConnectedAppService {

    @Autowired
    private CCConnectedAppRepo repo;

    private static final Logger log = LoggerFactory.getLogger(CCConnectedAppServiceImpl.class);

    @Override
    public Page<CCConnectedApp> getCCConnectedApp(Pageable pageable) {
        log.debug("Inside getCCConnectedApp");
        return repo.getConnectedApp(pageable);
    }

    @Override
    public List<CCConnectedApp> getCCConnectedAppById(Long id) {
        log.debug("Inside getCCConnectedAppById");
        return repo.getConnectedAppById(id);
    }

    @Override
    public CCConnectedApp createConnectedApp(CCConnectedApp ccConnectedApp) {
        log.debug("Inside createConnectedApp");
        ccConnectedApp.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", ccConnectedApp.toString());
        return repo.save(ccConnectedApp);
    }

    @Override
    public CCConnectedApp updateConnectedApp(CCConnectedApp ccConnectedApp, Long id) {
        log.debug("Inside updateConnectedApp");
        List<CCConnectedApp> connectAppById = getCCConnectedAppById(id);
        log.debug("Existing connectAppById: {}", connectAppById.toString());
        CCConnectedApp data = new CCConnectedApp();
        data.setId(id);
        data.setCcId(ccConnectedApp.getCcId() != 0 ? ccConnectedApp.getCcId() : connectAppById.getFirst().getCcId());
        data.setConnectedApp(ccConnectedApp.getConnectedApp() != null ? ccConnectedApp.getConnectedApp() : connectAppById.getFirst().getConnectedApp());
        data.setSubscription(ccConnectedApp.getSubscription() != null ? ccConnectedApp.getSubscription() : connectAppById.getFirst().getSubscription());
        data.setAutoDebit(ccConnectedApp.getAutoDebit() != null ? ccConnectedApp.getAutoDebit() : connectAppById.getFirst().getAutoDebit());
        data.setAmount(ccConnectedApp.getAmount() != null ? ccConnectedApp.getAmount() : connectAppById.getFirst().getAmount());
        data.setDate(ccConnectedApp.getDate() != null ? ccConnectedApp.getDate() : connectAppById.getFirst().getDate());
        data.setRemarks(ccConnectedApp.getRemarks() != null ? ccConnectedApp.getRemarks() : connectAppById.getFirst().getRemarks());
        data.setAddedBy(connectAppById.getFirst().getAddedBy());
        data.setDateAdded(connectAppById.getFirst().getDateAdded());
        data.setUpdateBy(ccConnectedApp.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated ccConnectedApp: {}", data.toString());

        return repo.save(data);
    }

    @Override
    public void deleteAllocation(Long id) {
        log.debug("Inside deleteAllocation");
        repo.deleteById(id);
    }

    @Override
    public Optional<CCConnectedApp> findById(Long id) {
        log.debug("Inside findById " + id);
        return repo.findById(id);
    }
}
