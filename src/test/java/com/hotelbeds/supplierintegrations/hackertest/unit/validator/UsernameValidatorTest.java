package com.hotelbeds.supplierintegrations.hackertest.unit.validator;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.validator.impl.UsernameValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsernameValidatorTest {
    UsernameValidator usernameValidator;

    @BeforeAll
    private void setUp() {
        usernameValidator = new UsernameValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Will.Smith",
            "Will Smith",
            "1234",
    })
    void validateUsername_whenIsValid_ThenReturnStringUsername(String username) {

        String validatedUsername = usernameValidator.validate(username);

        assertNotNull(validatedUsername);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "  ",
    })
    void validateUsername_whenIsInvalid_thenThrowsLogEntryValidationException(String username) {
        assertThrows(LogEntryValidationException.class, () -> usernameValidator.validate(username));
    }
}
