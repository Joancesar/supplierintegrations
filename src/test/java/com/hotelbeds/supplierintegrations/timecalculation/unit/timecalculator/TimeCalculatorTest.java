package com.hotelbeds.supplierintegrations.timecalculation.unit.timecalculator;

import com.hotelbeds.supplierintegrations.timecalculation.parser.impl.RFCTimestampParser;
import com.hotelbeds.supplierintegrations.timecalculation.timecalculator.impl.TimeCalculatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TimeCalculatorTest {
    @InjectMocks
    TimeCalculatorImpl timeCalculator;
    @Mock
    RFCTimestampParser rfcTimestampParser;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void minutesBetween_withValidTimeStamps_thenReturnDifference() {
        String timestamp1 = "Thu, 21 Dec 2023 16:01:07 +0200";
        String timestamp2 = "Thu, 21 Dec 2023 16:36:07 +0200";
        ZonedDateTime expectedZonedDate1 = ZonedDateTime.of(2023, 12, 21, 16, 1, 7, 0, ZoneOffset.ofHours(2));
        ZonedDateTime expectedZonedDate2 = ZonedDateTime.of(2023, 12, 21, 16, 36, 7, 0, ZoneOffset.ofHours(2));
        when(rfcTimestampParser.parse(timestamp1)).thenReturn(expectedZonedDate1);
        when(rfcTimestampParser.parse(timestamp2)).thenReturn(expectedZonedDate2);

        long difference = timeCalculator.minutesBetween(timestamp1, timestamp2);

        assertEquals(35, difference);
        verify(rfcTimestampParser).parse(timestamp1);
        verify(rfcTimestampParser).parse(timestamp2);
    }

    @Test
    void minutesBetween_whenFirstTimestampIsGreater_thenReturnDifferenceAbsolute() {
        String timestamp1 = "Thu, 21 Dec 2023 18:45:00 +0200";
        String timestamp2 = "Thu, 21 Dec 2023 16:00:00 +0200";
        ZonedDateTime expectedZonedDate1 = ZonedDateTime.of(2023, 12, 21, 18, 45, 0, 0, ZoneOffset.ofHours(2));
        ZonedDateTime expectedZonedDate2 = ZonedDateTime.of(2023, 12, 21, 16, 0, 0, 0, ZoneOffset.ofHours(2));
        when(rfcTimestampParser.parse(timestamp1)).thenReturn(expectedZonedDate1);
        when(rfcTimestampParser.parse(timestamp2)).thenReturn(expectedZonedDate2);

        long difference = timeCalculator.minutesBetween(timestamp1, timestamp2);

        assertEquals(165, difference);
        verify(rfcTimestampParser).parse(timestamp1);
        verify(rfcTimestampParser).parse(timestamp2);
    }

    @Test
    void minutesBetween_whenSecondTimestampIsGreater_thenReturnDifferenceAbsolute() {
        String timestamp1 = "Thu, 21 Dec 2023 16:00:00 +0200";
        String timestamp2 = "Thu, 21 Dec 2023 18:45:00 +0200";
        ZonedDateTime expectedZonedDate1 = ZonedDateTime.of(2023, 12, 21, 16, 0, 0, 0, ZoneOffset.ofHours(2));
        ZonedDateTime expectedZonedDate2 = ZonedDateTime.of(2023, 12, 21, 18, 45, 0, 0, ZoneOffset.ofHours(2));
        when(rfcTimestampParser.parse(timestamp1)).thenReturn(expectedZonedDate1);
        when(rfcTimestampParser.parse(timestamp2)).thenReturn(expectedZonedDate2);

        long difference = timeCalculator.minutesBetween(timestamp1, timestamp2);

        assertEquals(165, difference);
        verify(rfcTimestampParser).parse(timestamp1);
        verify(rfcTimestampParser).parse(timestamp2);
    }

    @Test
    void minutesBetween_withValidTimestamps_thenReturnDifferenceRoundedDown() {
        String timestamp1 = "Thu, 21 Dec 2023 16:00:00 +0200";
        String timestamp2 = "Thu, 21 Dec 2023 16:01:45 +0200";
        ZonedDateTime expectedZonedDate1 = ZonedDateTime.of(2023, 12, 21, 16, 0, 0, 0, ZoneOffset.ofHours(2));
        ZonedDateTime expectedZonedDate2 = ZonedDateTime.of(2023, 12, 21, 16, 1, 45, 0, ZoneOffset.ofHours(2));
        when(rfcTimestampParser.parse(timestamp1)).thenReturn(expectedZonedDate1);
        when(rfcTimestampParser.parse(timestamp2)).thenReturn(expectedZonedDate2);

        long difference = timeCalculator.minutesBetween(timestamp1, timestamp2);

        assertEquals(1, difference);
        verify(rfcTimestampParser).parse(timestamp1);
        verify(rfcTimestampParser).parse(timestamp2);
    }
}
