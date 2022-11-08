package me.elyor.memberservice.global.validation.constraint;

import me.elyor.memberservice.global.validation.validator.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The annotated element must be a lowercase english letters or digits
 * */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {

    String message() default "Username must be english letters or digits with minimum 5 and maximum 20 characters";

    /**
     * Minimum length of a {@code username}.
     * Default is 5
     * */
    int min() default 5;

    /**
     * Maximum length of a {@code username}.
     * Default is 20
     * */
    int max() default 20;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
