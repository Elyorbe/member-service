package me.elyor.memberservice.global.validation.validator;

import me.elyor.memberservice.global.validation.constraint.MobilePhone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MobilePhoneValidatorTests {

    Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenValidateWithValidInputThenNoViolations() {
        Input input = new Input();

        input.phoneNumber = "010-8732-2464";
        assertInputHasNoViolations(input);

        input.phoneNumber = "01077435678";
        assertInputHasNoViolations(input);
    }

    @Test
    void whenValidateWithInvalidInputThenExpectViolations() {
        Input input = new Input();

        input.phoneNumber = "010-87322464";
        assertInputHasViolations(input);

        input.phoneNumber = "blah-blah-blah";
        assertInputHasViolations(input);

        input.phoneNumber = "02-2132-4623";
        assertInputHasViolations(input);

        input.phoneNumber = "02-213-5234";
        assertInputHasViolations(input);

        input.phoneNumber = "010-abcd-dbca";
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

        @MobilePhone
        String phoneNumber;

    }

}