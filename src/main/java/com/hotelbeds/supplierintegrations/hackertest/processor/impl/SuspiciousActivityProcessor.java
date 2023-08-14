package com.hotelbeds.supplierintegrations.hackertest.processor.impl;

import com.hotelbeds.supplierintegrations.hackertest.cache.MultiMapCache;
import com.hotelbeds.supplierintegrations.hackertest.cache.impl.MultiValueMapCache;
import com.hotelbeds.supplierintegrations.hackertest.exception.ProcessorException;
import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.processor.Processor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SuspiciousActivityProcessor implements Processor<LogEntry, String> {

    private final MultiMapCache<String, Long> suspiciousActivityCache;
    private static final int MAX_FAILED_ATTEMPTS = 5;

    public SuspiciousActivityProcessor() {
        suspiciousActivityCache = new MultiValueMapCache<>();
    }

    @Override
    public String process(LogEntry logEntry) {
        String ip = logEntry.getIp();
        long epochTime = logEntry.getEpochTime();
        long currentEpoch = Instant.now().getEpochSecond();
        long fiveMinutesAgo = currentEpoch - 300;

        if (epochTime > currentEpoch) {
            throw new ProcessorException("Epoch time is in the future: " + epochTime);
        }

        suspiciousActivityCache.put(ip, epochTime);
        suspiciousActivityCache.removeAllIf(epoch -> epoch < fiveMinutesAgo);
        suspiciousActivityCache.removeIf(entry -> entry.getValue().isEmpty());

        if (suspiciousActivityCache.size(ip) >= MAX_FAILED_ATTEMPTS) {
            return ip;
        } else {
            return null;
        }
    }

}
