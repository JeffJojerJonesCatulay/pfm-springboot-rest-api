package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.CCDetails;
import com.pfm.restapi.repository.CCDetailsRepo;
import com.pfm.restapi.service.CCDetailsService;
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
public class CCDetailsServiceImpl implements CCDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CCDetailsServiceImpl.class);

    @Autowired
    private CCDetailsRepo ccDetailsRepo;

    @Override
    public Page<CCDetails> getCCDetails(Pageable pageable) {
        log.debug("Inside getCCDetails");
        return ccDetailsRepo.getCCDetails(pageable);
    }

    @Override
    public List<CCDetails> getCCDetailsById(Long id) {
        log.debug("Inside getCCDetailsById");
        return ccDetailsRepo.getCCDetailsById(id);
    }

    @Override
    public CCDetails createCCDetails(CCDetails ccDetails) {
        log.debug("Inside createCCDetails");
        ccDetails.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", ccDetails.toString());
        return ccDetailsRepo.save(ccDetails);
    }

    @Override
    public CCDetails updateCCDetails(CCDetails ccDetails, Long id) {
        log.debug("Inside updateCCDetails");
        List<CCDetails> ccDetailsID = getCCDetailsById(id);
        log.debug("Existing allocationCCDetailsId: {}", ccDetailsID.toString());
        CCDetails data = new CCDetails();
        data.setCcId(id);
        data.setCcName(ccDetails.getCcName() != null ? ccDetails.getCcName() : ccDetailsID.getFirst().getCcName());
        data.setCcLastDigit(ccDetails.getCcLastDigit() != null ? ccDetails.getCcLastDigit() : ccDetailsID.getFirst().getCcLastDigit());
        data.setCcAcronym(ccDetails.getCcAcronym() != null ? ccDetails.getCcAcronym() : ccDetailsID.getFirst().getCcAcronym());
        data.setAddedBy(ccDetailsID.getFirst().getAddedBy());
        data.setDateAdded(ccDetailsID.getFirst().getDateAdded());
        data.setUpdateBy(ccDetails.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated allocationMappingId: {}", data.toString());

        return ccDetailsRepo.save(data);
    }

    @Override
    public void deleteCCDetails(Long id) {
        log.debug("Inside deleteCCDetails");
        ccDetailsRepo.deleteById(id);
    }

    @Override
    public Optional<CCDetails> findById(Long id) {
        log.debug("Inside findById " + id);
        return ccDetailsRepo.findById(id);
    }

}
