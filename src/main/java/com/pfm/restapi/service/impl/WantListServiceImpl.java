package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.WantList;
import com.pfm.restapi.repository.WantListRepo;
import com.pfm.restapi.service.WantListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WantListServiceImpl implements WantListService {

    private static final Logger log = LoggerFactory.getLogger(WantListServiceImpl.class);

    @Autowired
    private WantListRepo wantListRepo;

    @Override
    public Page<WantList> getWantList(Pageable pageable) {
        log.debug("Inside getWantList");
        return wantListRepo.getWantList(pageable);
    }

    @Override
    public List<WantList> getWantListById(Long id) {
        log.debug("Inside getWantListById");
        return wantListRepo.getWantListById(id);
    }

    @Override
    public WantList createWantList(WantList wantList) {
        log.debug("Inside createWantList");
        wantList.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", wantList.toString());
        return wantListRepo.save(wantList);
    }

    @Override
    public WantList updateWantList(WantList wantList, Long id) {
        log.debug("Inside updateWantList");
        List<WantList> wantListExisting = getWantListById(id);
        log.debug("Existing wantList: {}", wantListExisting.toString());
        WantList data = new WantList();
        data.setId(id);
        data.setDateWanted(wantList.getDateWanted() != null ? wantList.getDateWanted() : wantListExisting.getFirst().getDateWanted());
        data.setItem(wantList.getItem() != null ? wantList.getItem() : wantListExisting.getFirst().getItem());
        data.setEstimatedPrice(wantList.getEstimatedPrice() != 0 ? wantList.getEstimatedPrice() : wantListExisting.getFirst().getEstimatedPrice());
        data.setAfford(wantList.getAfford() != null ? wantList.getAfford() : wantListExisting.getFirst().getAfford());
        data.setRemarks(wantList.getRemarks() != null ? wantList.getRemarks() : wantListExisting.getFirst().getRemarks());
        data.setStatus(wantList.getStatus() != null ? wantList.getStatus() : wantListExisting.getFirst().getStatus());
        data.setAddedBy(wantListExisting.getFirst().getAddedBy());
        data.setDateAdded(wantListExisting.getFirst().getDateAdded());
        data.setUpdateBy(wantList.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated wantList: {}", data.toString());
        return wantListRepo.save(data);
    }

    @Override
    public void deleteWantList(Long id) {
        log.debug("Inside deleteWantList");
        wantListRepo.deleteById(id);
    }

    @Override
    public Optional<WantList> findById(Long id) {
        log.debug("Inside findById {}", id);
        return wantListRepo.findById(id);
    }

    @Override
    public Page<WantList> findByCustomSearch(Pageable pageable, WantList wantList) {
        log.debug("Inside findByCustomSearch");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id", "dateWanted", "estimatedPrice", "dateAdded", "addedBy", "updateDate", "updateBy")
                .withMatcher("item", match -> match.contains().ignoreCase())
                .withMatcher("afford", match -> match.contains().ignoreCase())
                .withMatcher("remarks", match -> match.contains().ignoreCase())
                .withMatcher("status", match -> match.contains().ignoreCase());

        Example<WantList> example = Example.of(wantList, matcher);

        return wantListRepo.findAll(example, pageable);
    }

}
