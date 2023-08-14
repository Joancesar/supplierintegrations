package com.hotelbeds.supplierintegrations.timecalculation.unit.parser;

import com.hotelbeds.supplierintegrations.timecalculation.exception.TimestampParseException;
import com.hotelbeds.supplierintegrations.timecalculation.parser.impl.RFCTimestampParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RFCTimestampParserTest {
    private RFCTimestampParser parser;

    @BeforeEach
    void setUp() {
        parser = new RFCTimestampParser();
    }

    @Test
    void parse_whenGivenValidTimestamp_thenReturnsZonedDateTime() {
        String timestamp = "Thu, 21 Dec 2023 16:01:07 +0200";
        ZonedDateTime expectedZonedDate = ZonedDateTime.of(2023, 12, 21, 16, 1, 7, 0, ZoneOffset.ofHours(2));

        ZonedDateTime actualZonedDate = parser.parse(timestamp);

        assertEquals(expectedZonedDate, actualZonedDate);
    }

    @Test
    void parse_whenGivenInvalidTimestamp_thenThrowsException() {
        String invalidTimestamp = "Thu, Dec 21 2023 16:01:07 +0200";

        assertThrows(TimestampParseException.class, () -> parser.parse(invalidTimestamp));
    }

    @Test
    void parse_whenGivenTimestampAndTimeZoneNotIsUsLocale_thenThrowsException() {
        DateTimeFormatter rfc2822Formatter = DateTimeFormatter
                .ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", new Locale("es", "ES"));
        ZonedDateTime now = ZonedDateTime.now();
        String invalidTimestamp = now.format(rfc2822Formatter);

        assertThrows(TimestampParseException.class, () -> parser.parse(invalidTimestamp));
    }
}
