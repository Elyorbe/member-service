package me.elyor.memberservice.global.validation.validator;

import me.elyor.memberservice.global.validation.constraint.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsernameValidatorTests {

    Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenValidateWithValidInputThenNoViolations() {
        Input input = new Input();

        input.username = "echofor"; //lower case english letters only
        assertInputHasNoViolations(input);

        input.username = "123456"; //digits only
        assertInputHasNoViolations(input);

        input.username = "12usernamewithdigits"; //mixed
        assertInputHasNoViolations(input);
    }

    @Test
    void whenValidateWithInvalidInputThenExpectViolations() {
        Input input = new Input();

        input.username = "speci@lusern@me"; //special character
        assertInputHasViolations(input);

        input.username = "UsernameWithCapitalLetter";
        assertInputHasViolations(input);

        input.username = "user"; //less than 5 characters
        assertInputHasViolations(input);

        input.username = "veryveryveryveryverylongusername"; // more than 20 characters
        assertInputHasViolations(input);
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

        @Username
        String username;

    }

}