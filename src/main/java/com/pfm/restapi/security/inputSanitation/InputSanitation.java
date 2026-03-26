package com.pfm.restapi.security.inputSanitation;

import com.pfm.restapi.controller.SecurityController;
import com.pfm.restapi.utility.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class InputSanitation {
    private static final Logger log = LoggerFactory.getLogger(InputSanitation.class);
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("allocId", "allocation", "type", "status", "dateAdded");

    public void validateSortBy(String sortBy) {
        log.debug("validateSortBy - Start");
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }
        log.debug("validateSortBy - End");
    }

    public void validateSize(Integer size) {
        log.debug("validateSize - Start");
        if (size > Constant.MAX_SIZE_ALLOWED) {
            throw new IllegalArgumentException("Size can't exceed to " + Constant.MAX_SIZE_ALLOWED + " size passed: " + size);
        }
        log.debug("validateSize - End");
    }

    public void sanitizeInput(String input) {
        log.debug("sanitizeInput - Start");
        if (input != null) {
            // Disallow semicolon ;
            // Disallow single quote '
            // Disallow double quote "
            // Disallow SQL comment --
            // Disallow backslash \
            String unsafePattern = ".*[;'\"]|--|\\\\.*";
            if (input.matches(unsafePattern)) {
                throw new IllegalArgumentException("Invalid characters detected in input");
            }
        }
        log.debug("sanitizeInput - End");
    }

    public void validateNumeric(String input) {
        try {
            log.debug("validateNumeric - Start");
            Long.parseLong(input.trim());
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("Must be a number");
        } finally {
            log.debug("validateNumeric - End");
        }
    }
}
