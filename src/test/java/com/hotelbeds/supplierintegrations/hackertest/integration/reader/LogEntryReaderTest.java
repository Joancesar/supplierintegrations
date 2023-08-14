package com.hotelbeds.supplierintegrations.hackertest.integration.reader;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.model.enu.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.reader.impl.LogEntryReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LogEntryReaderTest {

    @Autowired
    LogEntryReader logEntryReader;

    @Test
    void readLine_whenLineHasValidFormat_thenReturnObject() {
        String line = "80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith";
        LogEntry expectedLogEntry = new LogEntry();
        expectedLogEntry.setIp("80.238.9.179");
        expectedLogEntry.setEpochTime(133612947);
        expectedLogEntry.setAction(LoginAction.SIGNIN_FAILURE);
        expectedLogEntry.setUsername("Will.Smith");

        LogEntry actualLogEntry = logEntryReader.readLine(line);

        assertNotNull(actualLogEntry);
        assertEquals(expectedLogEntry.getIp(), actualLogEntry.getIp());
        assertEquals(expectedLogEntry.getEpochTime(), actualLogEntry.getEpochTime());
        assertEquals(expectedLogEntry.getAction(), actualLogEntry.getAction());
        assertEquals(expectedLogEntry.getUsername(), actualLogEntry.getUsername());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidLines")
    void readLine_whenLineHasInvalidFormat_thenThrowsValidationException(String line, String errorMessage) {

        Exception exception = assertThrows(LogEntryValidationException.class, () -> logEntryReader.readLine(line));

        assertEquals(errorMessage, exception.getMessage());
    }

    private static Stream<Arguments> provideInvalidLines() {
        return Stream.of(
                Arguments.of("80.238.9.179,133612947,SIGNIN_FAILURE",
                        "Invalid format : 80.238.9.179,133612947,SIGNIN_FAILURE"),
                Arguments.of("80.238.9.179.3,133612947,SIGNIN_FAILURE,Will.Smith",
                        "Invalid ip format: 80.238.9.179.3"),
                Arguments.of("80.238.9.179,abc,SIGNIN_FAILURE,Will.Smith", "Invalid epoch time format: abc"),
                Arguments.of("80.238.9.179,133612947,OTHER,Will.Smith", "Invalid login action format: OTHER"),
                Arguments.of("80.238.9.179,133612947,SIGNIN_FAILURE,  ",
                        "Invalid username format: username is null or empty")
        );
    }
}
