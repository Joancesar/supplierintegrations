package com.hotelbeds.supplierintegrations.hackertest.validator.impl;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator implements Validator<String, String> {

    @Override
    public String validate(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new LogEntryValidationException("Invalid username format: username is null or empty");
        } else {
            return username;
        }
    }
}
