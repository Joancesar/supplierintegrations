package com.hotelbeds.supplierintegrations.hackertest.unit.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetector;
import com.hotelbeds.supplierintegrations.hackertest.detector.impl.HackerDetectorImpl;
import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.exception.ProcessorException;
import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.model.enu.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.processor.impl.SuspiciousActivityProcessor;
import com.hotelbeds.supplierintegrations.hackertest.reader.impl.LogEntryReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HackDetectorTest {

    @InjectMocks
    HackerDetectorImpl hackerDetector;
    @Mock
    LogEntryReader logEntryReader;
    @Mock
    SuspiciousActivityProcessor processor;

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
    void parseLine_whenSigninSuccess_thenParseCorrectly() {
        String line = "192.168.0.1,1628874000,SIGNIN_SUCCESS,user123";
        LogEntry logEntry = new LogEntry();
        logEntry.setIp("192.168.0.1");
        logEntry.setEpochTime(1628874000);
        logEntry.setAction(LoginAction.SIGNIN_SUCCESS);
        logEntry.setUsername("user123");
        when(logEntryReader.readLine(line)).thenReturn(logEntry);

        String actualParsed = hackerDetector.parseLine(line);

        assertNull(actualParsed);
        verify(logEntryReader).readLine(line);
        verifyNoInteractions(processor);
    }

    @Test
    void parseLine_whenSigninFailure_thenParseCorrectly() {
        String line = "192.168.0.1,1628874000,SIGNIN_FAILURE,user123";
        String expectedParsed = "192.168.0.1";
        LogEntry logEntry = new LogEntry();
        logEntry.setIp(expectedParsed);
        logEntry.setEpochTime(1628874000);
        logEntry.setAction(LoginAction.SIGNIN_FAILURE);
        logEntry.setUsername("user123");
        when(logEntryReader.readLine(line)).thenReturn(logEntry);
        when(processor.process(logEntry)).thenReturn(expectedParsed);

        String actualParsed = hackerDetector.parseLine(line);

        assertNotNull(actualParsed);
        assertEquals(expectedParsed, actualParsed);
        verify(logEntryReader).readLine(line);
        verify(processor).process(logEntry);
    }

    @Test
    void parseLine_whenValidationExceptionOccurs_thenParseMessage() {
        String line = ",,,";
        String expectedParsed = "Error Message";
        when(logEntryReader.readLine(line)).thenThrow(new LogEntryValidationException("Error Message"));

        String actualParsed = hackerDetector.parseLine(line);

        assertEquals(expectedParsed, actualParsed);
        verify(logEntryReader).readLine(line);
        verifyNoInteractions(processor);
    }

    @Test
    void parseLine_whenProcessorExceptionOccurs_thenParseMessage() {
        String line = "192.168.0.1,999999999999,SIGNIN_FAILURE,user123";
        String expectedParsed = "Error Message";
        LogEntry logEntry = new LogEntry();
        logEntry.setIp("192.168.0.1");
        logEntry.setEpochTime(1628874000);
        logEntry.setAction(LoginAction.SIGNIN_FAILURE);
        logEntry.setUsername("user123");
        when(logEntryReader.readLine(line)).thenReturn(logEntry);
        when(processor.process(logEntry)).thenThrow(new ProcessorException("Error Message"));

        String actualParsed = hackerDetector.parseLine(line);

        assertEquals(expectedParsed, actualParsed);
        verify(logEntryReader).readLine(line);
        verify(processor).process(logEntry);
    }
}
