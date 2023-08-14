package com.hotelbeds.supplierintegrations.timecalculation.parser.impl;

import com.hotelbeds.supplierintegrations.timecalculation.exception.TimestampParseException;
import com.hotelbeds.supplierintegrations.timecalculation.parser.TimestampParser;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Component
public class RFCTimestampParser implements TimestampParser {
    private final DateTimeFormatter rfc2822Formatter
            = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    @Override
    public ZonedDateTime parse(String timestamp) {
        try {
            if (timestamp == null) {
                throw new TimestampParseException("Timestamp was null");
            }
            return ZonedDateTime.parse(timestamp, rfc2822Formatter);
        } catch (DateTimeParseException dtpex) {
            throw new TimestampParseException("Invalid format", dtpex);
        }
    }
}
