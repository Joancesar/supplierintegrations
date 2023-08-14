package com.hotelbeds.supplierintegrations.hackertest.unit.validator;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.validator.impl.EpochTimeValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EpochTimeValidatorTest {
    EpochTimeValidator epochTimeValidator;

    @BeforeAll
    private void setUp() {
        epochTimeValidator = new EpochTimeValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1628874000",
            "9999999999",
            "0",
    })
    void validateEpochTime_whenIsValid_ThenReturnLongEpoch(String epochTime) {

        long validatedEpochTime = epochTimeValidator.validate(epochTime);

        assertEquals(Long.parseLong(epochTime), validatedEpochTime);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "abc",
            "-1",
            "-1628874000",
    })
    void validateEpochTime_whenIsInvalid_thenThrowsLogEntryValidationException(String epochTime) {
        assertThrows(LogEntryValidationException.class, () -> epochTimeValidator.validate(epochTime));
    }
}
