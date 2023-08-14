package com.hotelbeds.supplierintegrations.hackertest.validator.impl;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class EpochTimeValidator implements Validator<String, Long> {

    @Override
    public Long validate(String epochTime) {
        try {
            long parsedEpochTime = Long.parseLong(epochTime);
            if (parsedEpochTime < 0) {
                throw new LogEntryValidationException("Invalid format : epochTime is negative");
            }
            return parsedEpochTime;
        } catch (NumberFormatException e) {
            throw new LogEntryValidationException("Invalid epoch time format: " + epochTime);
        }
    }
}
