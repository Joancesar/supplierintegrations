package com.hotelbeds.supplierintegrations.hackertest.detector.impl;

import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetector;
import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.exception.ProcessorException;
import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.model.enu.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.processor.impl.SuspiciousActivityProcessor;
import com.hotelbeds.supplierintegrations.hackertest.reader.impl.LogEntryReader;
import org.springframework.stereotype.Service;

@Service
public class HackerDetectorImpl implements HackerDetector {

    private final LogEntryReader logEntryReader;
    private final SuspiciousActivityProcessor activityProcessor;

    public HackerDetectorImpl(LogEntryReader logEntryReader, SuspiciousActivityProcessor activityProcessor) {
        this.logEntryReader = logEntryReader;
        this.activityProcessor = activityProcessor;
    }

    @Override
    public String parseLine(String line) {
        try {
            LogEntry logEntry = logEntryReader.readLine(line);
            if (LoginAction.SIGNIN_FAILURE.equals(logEntry.getAction())) {
                return activityProcessor.process(logEntry);
            } else {
                return null;
            }
        } catch (LogEntryValidationException | ProcessorException vex) {
            return vex.getMessage();
        }

    }
}
