package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.NetWorth;
import com.pfm.restapi.repository.NetWorthRepo;
import com.pfm.restapi.service.NetWorthService;
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
public class NetWorthServiceImpl implements NetWorthService {

    private static final Logger log = LoggerFactory.getLogger(NetWorthServiceImpl.class);

    @Autowired
    private NetWorthRepo netWorthRepo;

    @Override
    public Page<NetWorth> getNetWorth(Pageable pageable) {
        log.debug("Inside getNetWorth");
        return netWorthRepo.getNetWorth(pageable);
    }

    @Override
    public List<NetWorth> getNetWorthByAllocId(Long id) {
        log.debug("Inside getNetWorthByAllocId");
        return netWorthRepo.getNetWorthByAllocId(id);
    }

    @Override
    public NetWorth createNetWorth(NetWorth netWorth) {
        log.debug("Inside createNetWorth");
        netWorth.setDateAdded(String.valueOf(LocalDateTime.now()));
        log.debug("data: {}", netWorth.toString());
        return netWorthRepo.save(netWorth);
    }

    @Override
    public NetWorth updateNetWorth(NetWorth netWorth, Long id) {
        log.debug("Inside updateNetWorth");
        List<NetWorth> netWorthExisting = getNetWorthByAllocId(id);
        log.debug("Existing netWorth: {}", netWorthExisting.toString());
        NetWorth data = new NetWorth();
        data.setId(id);
        data.setAllocId(netWorth.getAllocId() != 0 ? netWorth.getAllocId() : netWorthExisting.getFirst().getAllocId());
        data.setMonth(netWorth.getMonth() != null ? netWorth.getMonth() : netWorthExisting.getFirst().getMonth());
        data.setYear(netWorth.getYear() != 0 ? netWorth.getYear() : netWorthExisting.getFirst().getYear());
        data.setValue(netWorth.getValue() != 0 ? netWorth.getValue() : netWorthExisting.getFirst().getValue());
        data.setAddedBy(netWorthExisting.getFirst().getAddedBy());
        data.setDateAdded(netWorthExisting.getFirst().getDateAdded());
        data.setUpdateBy(netWorth.getUpdateBy());
        data.setUpdateDate(String.valueOf(LocalDateTime.now()));
        log.debug("Updated netWorth: {}", data.toString());
        return netWorthRepo.save(data);
    }

    @Override
    public void deleteNetWorth(Long id) {
        log.debug("Inside deleteNetWorth");
        netWorthRepo.deleteById(id);
    }

    @Override
    public List<NetWorth> getNetWorthByMonthYear(String month, Long year) {
        log.debug("Inside getNetWorthByMonthYear Month: {} Year: {}", month, year);
        return netWorthRepo.getNetWorthByMonthYear(month, year);
    }

    @Override
    public Optional<NetWorth> findById(Long id) {
        log.debug("Inside findById {}", id);
        return netWorthRepo.findById(id);
    }
}
