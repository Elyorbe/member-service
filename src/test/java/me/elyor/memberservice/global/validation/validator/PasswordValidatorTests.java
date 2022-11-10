package me.elyor.memberservice.global.validation.validator;

import me.elyor.memberservice.global.validation.constraint.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordValidatorTests {

    Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenValidateWithInvalidPasswordThenExpectError() {
        Input input = new Input();

        input.password = "123ab"; // fewer than 8 characters
        assertInputHasViolations(input);

        input.password = "abced12345abced12345iew"; // more than 20 characters
        assertInputHasViolations(input);

        input.password = "12345678"; // digits only
        assertInputHasViolations(input);

        input.password = "lowercaseonly"; // lowercase characters only
        assertInputHasViolations(input);

        input.password = "UPPERCASEONLY"; // uppercase characters only
        assertInputHasViolations(input);

        input.password = "Nodigitp@ss"; // no digits
        assertInputHasViolations(input);


        input.password = "Only1specialch@r"; // at least 1 upper/lowercase and digit but only 1 special char
        assertInputHasViolations(input);
    }

    @Test
    void whenValidateWithValidPasswordThenNoError() {
        Input input = new Input();

        input.password = "V@lidp@88word";
        assertInputHasNoViolations(input);

        input.password = "@n%therP9";
        assertInputHasNoViolations(input);

        input.password = "1pa$$Word";
        assertInputHasNoViolations(input);

        input.password = "space P@88word"; //https://owasp.org/www-community/password-special-characters
        assertInputHasNoViolations(input);
    }

        private void assertInputHasNoViolations(Input input) {
        Set<ConstraintViolation<Input>> violations = validator.validate(input);
        assertTrue(violations.isEmpty());
    }

    private void assertInputHasViolations(Input input) {
        Set<ConstraintViolation<Input>> violations = validator.validate(input);
        assertFalse(violations.isEmpty());
    }

    static class Input {

        @Password
        String password;

    }

}
