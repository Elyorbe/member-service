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

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "(?=.*[0-9])" // at least one digit
            + "(?=.*[a-z])" // at least one lowercase letter
            + "(?=.*[A-Z])" // at least one capital letter
            + "(?:.*[ !\"#$%&'()*+,-.\\/:;<=>?@\\[\\]^_`{|}~]){2}" // at least 2 special characters
    , Pattern.MULTILINE);

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

        return PASSWORD_PATTERN.matcher(value).find();
    }

}
