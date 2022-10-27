package com.icesi.edu.users.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<CustomAnnotations.ValidPhoneAndCode, String> {
    @Override
    public void initialize(CustomAnnotations.ValidPhoneAndCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
