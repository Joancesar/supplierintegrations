package com.hotelbeds.supplierintegrations.hackertest.unit.validator;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.validator.impl.LineStructureValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LineStructureValidatorTest {

    LineStructureValidator lineStructureValidator;

    @BeforeAll
    private void setUp() {
        lineStructureValidator = new LineStructureValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "192.168.0.1,1628874000,SIGNIN_SUCCESS,user123",
            " 192.168.0.1, 1628874000,SIGNIN_SUCCESS ,user123 ",
    })
    void validateLine_whenIsValid_ThenReturnStringTokenizer(String line) {

        StringTokenizer tokenizer = lineStructureValidator.validate(line);

        assertNotNull(tokenizer);
        assertEquals(4, tokenizer.countTokens());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            ",,,",
            ",1628874000,SIGNIN_SUCCESS,user123",
            "192.168.0.1,,SIGNIN_SUCCESS,user123",
            "192.168.0.1,1628874000,,user123",
            "192.168.0.1,1628874000,SIGNIN_SUCCESS,"
    })
    void validateLine_whenIsInvalid_thenThrowsLogEntryValidationException(String line) {
        assertThrows(LogEntryValidationException.class, () -> lineStructureValidator.validate(line));
    }

}
