package com.icesi.edu.users.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<CustomAnnotations.ValidPhoneAndCode, String> {
    @Override
    public void initialize(CustomAnnotations.ValidPhoneAndCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (phone == null) return false;
        return phone.substring(0, 3).equals("+57") && phone.length() == 13 && !phone.matches("[^0-9+]");
    }
}
