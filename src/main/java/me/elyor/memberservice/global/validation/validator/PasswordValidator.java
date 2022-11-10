package me.elyor.memberservice.global.validation.validator;

import me.elyor.memberservice.global.validation.constraint.Password;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    /*
    * Whitespaces are allowed according to OWASP:
    * https://owasp.org/www-community/password-special-characters
    * */

    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern LOWER_CASE_ENGLISH_CHAR_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern UPPER_CASE_ENGLISH_CHAR_PATTERN = Pattern.compile("[A-Z]");
    public static final Pattern SPECIAL_CHAR_PATTERN =
            Pattern.compile("[ !\"#$%&'()*+,-.\\/:;<=>?@\\[\\]^_`{|}~]");

    private int min;
    private int max;

    @Override
    public void initialize(Password password) {
        this.min = password.min();
        this.max = password.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value) ||
                value.length() < min || value.length() > max)
            return false;

        return hasDigit(value) && hasLowerUppercaseChars(value)
                && hasAtLeastTwoSpecialChars(value);
    }

    private boolean hasDigit(String value) {
        return DIGIT_PATTERN.matcher(value).find();
    }

    private boolean hasLowerUppercaseChars(String value) {
        return LOWER_CASE_ENGLISH_CHAR_PATTERN.matcher(value).find()
                && UPPER_CASE_ENGLISH_CHAR_PATTERN.matcher(value).find();
    }

    private boolean hasAtLeastTwoSpecialChars(String value) {
        return SPECIAL_CHAR_PATTERN.matcher(value)
                .results().count() >= 2;
    }

}
