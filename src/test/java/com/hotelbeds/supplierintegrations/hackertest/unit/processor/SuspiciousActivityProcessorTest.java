package com.hotelbeds.supplierintegrations.hackertest.unit.processor;

import com.hotelbeds.supplierintegrations.hackertest.exception.ProcessorException;
import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.processor.impl.SuspiciousActivityProcessor;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class SuspiciousActivityProcessorTest {

    SuspiciousActivityProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new SuspiciousActivityProcessor();
    }

    @Test
    void process_withSameIpFourTimes_thenAllReturnNull() {
        long currentEpoch = Instant.now().getEpochSecond();
        long fiveMinutesAgo = currentEpoch - 300;
        long epochInRange = fiveMinutesAgo + 150;
        LogEntry logEntry = new LogEntry();
        logEntry.setIp("192.168.1.1");
        logEntry.setEpochTime(epochInRange);

        String firstString = processor.process(logEntry);
        String secondString = processor.process(logEntry);
        String thirdString = processor.process(logEntry);
        String fourthString = processor.process(logEntry);

        assertNull(firstString);
        assertNull(secondString);
        assertNull(thirdString);
        assertNull(fourthString);
    }

    @Test
    void process_withSameIpFiveTimes_thenFifthReturnsSuspiciousIp() {
        long currentEpoch = Instant.now().getEpochSecond();
        long fiveMinutesAgo = currentEpoch - 300;
        long epochInRange = fiveMinutesAgo + 150;
        LogEntry logEntry = new LogEntry();
        logEntry.setIp("192.168.1.1");
        logEntry.setEpochTime(epochInRange);

        String firstString = processor.process(logEntry);
        String secondString = processor.process(logEntry);
        String thirdString = processor.process(logEntry);
        String fourthString = processor.process(logEntry);
        String fifthString = processor.process(logEntry);

        assertNull(firstString);
        assertNull(secondString);
        assertNull(thirdString);
        assertNull(fourthString);
        assertNotNull(fifthString);
        assertEquals(logEntry.getIp(), fifthString);
    }

    @Test
    void process_withSameIpSixTimesAndWaitsUntilOneEpochIsOld_thenSixthReturnsSuspiciousIp() {
        long epochInRange = Instant.now().getEpochSecond() - 295;
        LogEntry firstLogEntry = new LogEntry();
        firstLogEntry.setIp("192.168.1.1");
        firstLogEntry.setEpochTime(epochInRange);

        String firstString = processor.process(firstLogEntry);

        await().during(10, SECONDS).atMost(11, SECONDS).until(() -> true);
        epochInRange = Instant.now().getEpochSecond() - 150;

        LogEntry secondLogEntry = new LogEntry();
        secondLogEntry.setIp("192.168.1.1");
        secondLogEntry.setEpochTime(epochInRange);
        String secondString = processor.process(secondLogEntry);
        String thirdString = processor.process(secondLogEntry);
        String fourthString = processor.process(secondLogEntry);
        String fifthString = processor.process(secondLogEntry);
        String sixthString = processor.process(secondLogEntry);

        assertNull(firstString);
        assertNull(secondString);
        assertNull(thirdString);
        assertNull(fourthString);
        assertNull(fifthString);
        assertNotNull(sixthString);
        assertEquals(secondLogEntry.getIp(), sixthString);
    }

    @Test
    void process_withEpochTimeFromFuture_thenThrowsProcessorException() {
        long currentEpoch = Instant.now().getEpochSecond();
        long epochFuture = currentEpoch + 200;
        LogEntry logEntry = new LogEntry();
        logEntry.setIp("192.168.1.1");
        logEntry.setEpochTime(epochFuture);

        assertThrows(ProcessorException.class, () -> processor.process(logEntry));
    }


}
