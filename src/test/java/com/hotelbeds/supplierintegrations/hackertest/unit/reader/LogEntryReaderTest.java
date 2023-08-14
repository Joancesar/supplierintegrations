package com.hotelbeds.supplierintegrations.hackertest.unit.reader;

import com.hotelbeds.supplierintegrations.hackertest.factory.LogEntryFactory;
import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.model.enu.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.reader.impl.LogEntryReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogEntryReaderTest {

    @InjectMocks
    LogEntryReader logEntryReader;
    @Mock
    LogEntryFactory logEntryFactory;

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
    void readLine_whenLineHasValidFormat_thenReturnObject() {
        //given
        String line = "192.168.0.1,1628874000,SIGNIN_SUCCESS,user123";
        LogEntry expectedLogEntry = new LogEntry();
        expectedLogEntry.setIp("192.168.0.1");
        expectedLogEntry.setEpochTime(1628874000);
        expectedLogEntry.setAction(LoginAction.SIGNIN_SUCCESS);
        expectedLogEntry.setUsername("user123");
        when(logEntryFactory.create(line)).thenReturn(expectedLogEntry);

        //when
        LogEntry logEntry = logEntryReader.readLine(line);

        //then
        assertNotNull(logEntry);
        assertEquals("192.168.0.1", logEntry.getIp());
        assertEquals(1628874000, logEntry.getEpochTime());
        assertEquals(LoginAction.SIGNIN_SUCCESS, logEntry.getAction());
        assertEquals("user123", logEntry.getUsername());
        verify(logEntryFactory).create(line);
    }

}
