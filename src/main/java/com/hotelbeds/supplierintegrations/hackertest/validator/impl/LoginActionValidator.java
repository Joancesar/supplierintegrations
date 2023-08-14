package com.hotelbeds.supplierintegrations.hackertest.validator.impl;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.model.enu.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class LoginActionValidator implements Validator<String, LoginAction> {
    @Override
    public LoginAction validate(String loginAction) {
        try {
            if (loginAction == null) {
                throw new LogEntryValidationException("Invalid format : loginAction was null");
            }
            return LoginAction.valueOf(loginAction);
        } catch (IllegalArgumentException e) {
            throw new LogEntryValidationException("Invalid login action format: " + loginAction);
        }
    }
}
