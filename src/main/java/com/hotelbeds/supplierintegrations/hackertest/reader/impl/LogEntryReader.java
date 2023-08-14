package com.hotelbeds.supplierintegrations.hackertest.reader.impl;

import com.hotelbeds.supplierintegrations.hackertest.factory.LogEntryFactory;
import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.reader.Reader;
import org.springframework.stereotype.Component;

@Component
public class LogEntryReader implements Reader<LogEntry> {

    private final LogEntryFactory logEntryFactory;

    public LogEntryReader(LogEntryFactory logEntryFactory) {
        this.logEntryFactory = logEntryFactory;
    }

    @Override
    public LogEntry readLine(String line) {
        return logEntryFactory.create(line);
    }

}
