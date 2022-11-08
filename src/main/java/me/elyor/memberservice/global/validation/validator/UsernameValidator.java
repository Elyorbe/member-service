package me.elyor.memberservice.global.validation.validator;

import me.elyor.memberservice.global.validation.constraint.Username;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9]*$");
    private int min;
    private int max;

    @Override
    public void initialize(Username username) {
        this.min = username.min();
        this.max = username.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value) ||
                value.length() < min || value.length() > max)
            return false;

        return USERNAME_PATTERN.matcher(value).matches();
    }

}
