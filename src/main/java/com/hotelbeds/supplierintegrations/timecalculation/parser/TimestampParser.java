package com.hotelbeds.supplierintegrations.timecalculation.parser;

import java.time.ZonedDateTime;

public interface TimestampParser {
    ZonedDateTime parse(String timestamp);
}
