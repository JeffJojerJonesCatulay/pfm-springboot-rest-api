package com.pfm.restapi.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class TpsMonitor {
    private static final Logger log = LoggerFactory.getLogger("com.pfm.tps");

    // Store start times keyed by API name + input
    private final ConcurrentHashMap<String, Long> startTimes = new ConcurrentHashMap<>();

    /**
     * Call this at the beginning of your API logic (inside try).
     * @param apiName name of the API endpoint
     * @param input   input parameters (or a summary string)
     */
    public void start(String apiName, String input) {
        String key = buildKey(apiName, input);
        startTimes.put(key, System.currentTimeMillis());
    }

    /**
     * Call this at the end of your API logic (inside finally).
     * @param apiName name of the API endpoint
     * @param input   input parameters (or a summary string)
     */
    public void end(String apiName, String input) {
        String key = buildKey(apiName, input);
        Long start = startTimes.remove(key);

        if (start != null) {
            long elapsed = System.currentTimeMillis() - start;
            log.info("API [{}] | input [{}] executed in {} ms", apiName, input, elapsed);
        } else {
            log.warn("End called without matching start for API [{}] | input [{}]", apiName, input);
        }
    }

    private String buildKey(String apiName, String input) {
        return apiName + "::" + input;
    }
}