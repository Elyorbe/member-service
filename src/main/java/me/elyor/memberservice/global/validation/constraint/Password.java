package me.elyor.memberservice.global.validation.constraint;

import me.elyor.memberservice.global.validation.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default "Invalid password";

    /**
     * Minimum length of a {@code password}.
     * Default is 5
     * */
    int min() default 8;

    /**
     * Maximum length of a {@code password}.
     * Default is 20
     * */
    int max() default 20;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
