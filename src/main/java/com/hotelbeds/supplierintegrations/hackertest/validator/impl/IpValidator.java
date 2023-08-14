package com.hotelbeds.supplierintegrations.hackertest.validator.impl;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class IpValidator implements Validator<String, String> {

    private static final String IP_REGEX = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
    private final Pattern pattern = Pattern.compile(IP_REGEX);

    @Override
    public String validate(String ip) {
        if (ip == null || !pattern.matcher(ip).matches()) {
            throw new LogEntryValidationException("Invalid ip format: " + ip);
        }
        return ip;
    }
}
