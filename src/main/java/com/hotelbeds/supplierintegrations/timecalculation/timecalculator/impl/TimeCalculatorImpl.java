package com.hotelbeds.supplierintegrations.timecalculation.timecalculator.impl;

import com.hotelbeds.supplierintegrations.timecalculation.parser.TimestampParser;
import com.hotelbeds.supplierintegrations.timecalculation.timecalculator.TimeCalculator;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class TimeCalculatorImpl implements TimeCalculator {

    private final TimestampParser parser;

    public TimeCalculatorImpl(TimestampParser parser) {
        this.parser = parser;
    }

    public long minutesBetween(String time1, String time2) {
        ZonedDateTime dateTime1 = parser.parse(time1);
        ZonedDateTime dateTime2 = parser.parse(time2);

        long difference = ChronoUnit.MINUTES.between(dateTime1, dateTime2);

        return Math.abs(difference);
    }
}
