package com.hotelbeds.supplierintegrations.hackertest.factory;

import com.hotelbeds.supplierintegrations.hackertest.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.validator.impl.*;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
public class LogEntryFactory {
    private final IpValidator ipValidator;
    private final EpochTimeValidator epochTimeValidator;
    private final LoginActionValidator loginActionValidator;
    private final UsernameValidator usernameValidator;
    private final LineStructureValidator lineStructureValidator;

    public LogEntryFactory(IpValidator ipValidator, EpochTimeValidator epochTimeValidator,
                           LoginActionValidator loginActionValidator, UsernameValidator usernameValidator,
                           LineStructureValidator lineStructureValidator) {
        this.ipValidator = ipValidator;
        this.epochTimeValidator = epochTimeValidator;
        this.loginActionValidator = loginActionValidator;
        this.usernameValidator = usernameValidator;
        this.lineStructureValidator = lineStructureValidator;
    }

    public LogEntry create(String line) {
        LogEntry entry = new LogEntry();
        StringTokenizer tokenizer = lineStructureValidator.validate(line);
        entry.setIp(ipValidator.validate(tokenizer.nextToken()));
        entry.setEpochTime(epochTimeValidator.validate(tokenizer.nextToken()));
        entry.setAction(loginActionValidator.validate(tokenizer.nextToken()));
        entry.setUsername(usernameValidator.validate(tokenizer.nextToken()));
        return entry;
    }
}