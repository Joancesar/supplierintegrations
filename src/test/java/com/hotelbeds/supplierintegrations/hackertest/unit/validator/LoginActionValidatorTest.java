package com.hotelbeds.supplierintegrations.hackertest.unit.validator;

import com.hotelbeds.supplierintegrations.hackertest.exception.LogEntryValidationException;
import com.hotelbeds.supplierintegrations.hackertest.model.enu.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.validator.impl.LoginActionValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginActionValidatorTest {

    LoginActionValidator loginActionValidator;

    @BeforeAll
    private void setUp() {
        loginActionValidator = new LoginActionValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "SIGNIN_FAILURE",
            "SIGNIN_SUCCESS",
    })
    void validateLoginAction_whenIsValid_ThenReturnLoginAction(String loginAction) {

        LoginAction validatedLoginAction = loginActionValidator.validate(loginAction);

        assertNotNull(validatedLoginAction);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "OTHER",
    })
    void validateLoginAction_whenIsInvalid_thenThrowsLogEntryValidationException(String loginAction) {
        assertThrows(LogEntryValidationException.class, () -> loginActionValidator.validate(loginAction));
    }
}
