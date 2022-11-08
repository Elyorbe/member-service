package me.elyor.memberservice.global.validation.constraint;

import me.elyor.memberservice.global.validation.validator.MobilePhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The annotated field must be a mobile phone number
 * in the form of either  {@code 010XXXXXXXX} or
 * {@code 010-XXXX-XXXX} where {@code X} is a digit
 * */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobilePhoneValidator.class)
public @interface MobilePhone {

    String message() default "Mobile phone number must be in the form of 010XXXXXXXX or 010-XXXX-XXXX";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Defines whether input phone number is optional.
     * If set to true, validation is performed only iff input string is
     * not null or not empty. That is, input value is either valid phone number
     * or blank. Defaults to false: null or empty values are considered to be not valid.
     * @return boolean
     */
    boolean optional() default false;

}
