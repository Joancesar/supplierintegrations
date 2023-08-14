package com.hotelbeds.supplierintegrations.hackertest.unit.validator;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.validator.impl.IpValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IpValidatorTest {

    IpValidator ipValidator;

    @BeforeAll
    private void setUp() {
        ipValidator = new IpValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1.0.0.1",
            "192.168.0.1",
            "255.255.255.255",
    })
    void validateIp_whenIsValid_ThenReturnStringIp(String ip) {

        String validatedIp = ipValidator.validate(ip);

        assertNotNull(validatedIp);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            " 192.168.0.1 ",
            "192.168.0.1.5",
            "192.168.0",
            "256.0.0.1"
    })
    void validateIp_whenIsInvalid_thenThrowsLogEntryValidationException(String ip) {
        assertThrows(LogEntryValidationException.class, () -> ipValidator.validate(ip));
    }
}
