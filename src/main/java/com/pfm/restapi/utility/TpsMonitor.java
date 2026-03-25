package com.pfm.restapi.utility;

import com.pfm.restapi.entity.RequestLogs;
import com.pfm.restapi.service.RequestLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

public class TpsMonitor {
    private static final Logger log = LoggerFactory.getLogger("com.pfm.tps");

    @Autowired
    private RequestLogsService requestLogsService;

    private final ConcurrentHashMap<String, Long> startTimes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> startElapsed = new ConcurrentHashMap<>();
    
    public void start(String apiName, String input) {
        String key = buildKey(apiName, input);
        startTimes.put(key, System.currentTimeMillis());
    }

    public String end(String apiName, String input, String returnStatus) {
        String key = buildKey(apiName, input);
        Long start = startTimes.remove(key);
        long elapsed = 0;

        if (start != null) {
            elapsed = System.currentTimeMillis() - start;
            log.info("API [{}] | input [{}] return [{}] executed in {} ms", apiName, input, returnStatus, elapsed);
        } else {
            log.warn("End called without matching start for API [{}] | input [{}]", apiName, input);
        }

        return String.valueOf(elapsed) + " ms";
    }

    private String buildKey(String apiName, String input) {
        return apiName + "::" + input;
    }
}