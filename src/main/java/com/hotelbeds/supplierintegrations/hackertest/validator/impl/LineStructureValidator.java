package com.hotelbeds.supplierintegrations.hackertest.validator.impl;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
public class LineStructureValidator implements Validator<String, StringTokenizer> {

    @Override
    public StringTokenizer validate(String line) {
        if (line == null) {
            throw new LogEntryValidationException("Invalid format : line was null");
        }
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        if (tokenizer.countTokens() != 4) {
            throw new LogEntryValidationException("Invalid format : " + line);
        }
        return tokenizer;
    }
}
