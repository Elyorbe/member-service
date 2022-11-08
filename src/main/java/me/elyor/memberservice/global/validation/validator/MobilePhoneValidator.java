package me.elyor.memberservice.global.validation.validator;

import me.elyor.memberservice.global.validation.constraint.MobilePhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MobilePhoneValidator implements ConstraintValidator<MobilePhone, String> {

    private static final Pattern MOBILE_PHONE_PATTERN =
            Pattern.compile("^(010)(\\d{8})$|^(010)-(\\d{4})-(\\d{4})$");

    private boolean optional;

    @Override
    public void initialize(MobilePhone mobilePhone) {
        this.optional = mobilePhone.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(optional && (value == null || value.isEmpty()))
            return true;

        return MOBILE_PHONE_PATTERN.matcher(value).matches();
    }

}
