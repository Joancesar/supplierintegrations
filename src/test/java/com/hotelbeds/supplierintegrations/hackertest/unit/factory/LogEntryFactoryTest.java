package com.hotelbeds.supplierintegrations.hackertest.unit.factory;

import com.hotelbeds.supplierintegrations.hackertest.factory.LogEntryFactory;
import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.model.enu.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.reader.impl.LogEntryReader;
import com.hotelbeds.supplierintegrations.hackertest.validator.impl.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogEntryFactoryTest {

    @InjectMocks
    LogEntryFactory logEntryFactory;
    @Mock
    IpValidator ipValidator;
    @Mock
    EpochTimeValidator epochTimeValidator;
    @Mock
    LoginActionValidator loginActionValidator;
    @Mock
    UsernameValidator usernameValidator;
    @Mock
    LineStructureValidator lineStructureValidator;

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
    void create_whenHasValidFormat_ThenReturnObject() {
        //given
        String line = "192.168.0.1,1628874000,SIGNIN_SUCCESS,user123";
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        LogEntry expectedLogEntry = new LogEntry();
        expectedLogEntry.setIp("192.168.0.1");
        expectedLogEntry.setEpochTime(1628874000);
        expectedLogEntry.setAction(LoginAction.SIGNIN_SUCCESS);
        expectedLogEntry.setUsername("user123");
        when(lineStructureValidator.validate(line)).thenReturn(tokenizer);
        when(ipValidator.validate(anyString())).thenReturn("192.168.0.1");
        when(epochTimeValidator.validate(anyString())).thenReturn(1628874000L);
        when(loginActionValidator.validate(anyString())).thenReturn(LoginAction.SIGNIN_SUCCESS);
        when(usernameValidator.validate(anyString())).thenReturn("user123");

        //when
        LogEntry logEntry = logEntryFactory.create(line);

        //then
        assertNotNull(logEntry);
        assertEquals(expectedLogEntry.getIp(), logEntry.getIp());
        assertEquals(expectedLogEntry.getEpochTime(), logEntry.getEpochTime());
        assertEquals(expectedLogEntry.getAction(), logEntry.getAction());
        assertEquals(expectedLogEntry.getUsername(), logEntry.getUsername());
        verify(lineStructureValidator).validate(line);
        verify(ipValidator).validate("192.168.0.1");
        verify(epochTimeValidator).validate("1628874000");
        verify(loginActionValidator).validate("SIGNIN_SUCCESS");
        verify(usernameValidator).validate("user123");
    }

}
