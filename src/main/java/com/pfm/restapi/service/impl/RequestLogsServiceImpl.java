package com.pfm.restapi.service.impl;

import com.pfm.restapi.entity.RequestLogs;
import com.pfm.restapi.repository.RequestLogsRepo;
import com.pfm.restapi.service.RequestLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestLogsServiceImpl implements RequestLogsService {
    @Autowired
    private RequestLogsRepo requestLogsRepo;

    @Override
    public RequestLogs inputLogs(RequestLogs request) {
        return requestLogsRepo.save(request);
    }
}
