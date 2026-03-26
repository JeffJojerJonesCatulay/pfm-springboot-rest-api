package com.pfm.restapi.service;

import com.pfm.restapi.entity.RequestLogs;
import org.springframework.stereotype.Service;

@Service
public interface RequestLogsService {
    public RequestLogs inputLogs(RequestLogs request);
}
