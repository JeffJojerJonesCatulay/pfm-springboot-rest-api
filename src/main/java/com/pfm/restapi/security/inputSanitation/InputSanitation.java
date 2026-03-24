package com.pfm.restapi.security.inputSanitation;

import com.pfm.restapi.utility.Constant;

import java.util.Set;

public class InputSanitation {
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("allocId", "allocation", "type", "status", "dateAdded");

    public void validateSortBy(String sortBy) {
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }
    }

    public void validateSize(Integer size) {
        if (size > Constant.MAX_SIZE_ALLOWED) {
            throw new IllegalArgumentException("Size can't exceed to " + Constant.MAX_SIZE_ALLOWED + " size passed: " + size);
        }
    }

    public void sanitizeInput(String input) {
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
    }

    public void validateNumeric(String input) {
        try {
            Long.parseLong(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Must be a number");
        }
    }
}
