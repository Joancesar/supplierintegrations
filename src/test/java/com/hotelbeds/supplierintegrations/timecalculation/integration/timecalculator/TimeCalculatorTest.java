package com.hotelbeds.supplierintegrations.timecalculation.integration.timecalculator;

import com.hotelbeds.supplierintegrations.timecalculation.exception.TimestampParseException;
import com.hotelbeds.supplierintegrations.timecalculation.timecalculator.impl.TimeCalculatorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TimeCalculatorTest {

    @Autowired
    TimeCalculatorImpl timeCalculator;

    @Test
    void minutesBetween_withSameTimeAndSameTimeZone_thenReturnZero() {
        String time1 = "Thu, 21 Dec 2023 14:00:00 +0000";
        String time2 = "Thu, 21 Dec 2023 14:00:00 +0000";

        long minutes = timeCalculator.minutesBetween(time1, time2);

        assertEquals(0, minutes);
    }

    @Test
    void minutesBetween_withSameTimeAndDifferentTimeZones_thenReturnZero() {
        String time1 = "Thu, 21 Dec 2000 14:00:00 +0000";
        String time2 = "Thu, 21 Dec 2000 16:00:00 +0200";

        long minutes = timeCalculator.minutesBetween(time1, time2);

        assertEquals(0, minutes);
    }

    @Test
    void minutesBetween_withDifferentTimeAndSameTimeZones_thenReturnDifference() {
        String time1 = "Thu, 21 Dec 2023 14:30:00 +0000";
        String time2 = "Thu, 21 Dec 2023 14:00:00 +0000";

        long minutes = timeCalculator.minutesBetween(time1, time2);

        assertEquals(30, minutes);
    }

    @Test
    void minutesBetween_withDifferentTimeAndDifferentTimeZones_thenReturnDifference() {
        String time1 = "Thu, 21 Dec 2023 14:30:00 +0000";
        String time2 = "Thu, 21 Dec 2023 14:00:00 +0400";

        long minutes = timeCalculator.minutesBetween(time1, time2);

        assertEquals(270, minutes);
    }

    @Test
    void minutesBetween_withInvalidTimestamp_thenThrowsException() {
        String time1 = "Jue, 21 Dic 2023 14:30:00 +0000";
        String time2 = "Thu, 21 Dec 2023 14:00:00 +0400";

        assertThrows(TimestampParseException.class, () -> timeCalculator.minutesBetween(time1, time2));
    }

    @Test
    void minutesBetween_withTimestampNull_thenThrowsException() {
        String time1 = null;
        String time2 = "Thu, 21 Dec 2023 14:00:00 +0400";

        assertThrows(TimestampParseException.class, () -> timeCalculator.minutesBetween(time1, time2));
    }

}
