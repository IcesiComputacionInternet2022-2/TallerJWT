package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<CustomAnnotations.PhoneValidation, String> {

    @Override
    public void initialize(CustomAnnotations.PhoneValidation phoneValidation) {
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile("^(\\+57)[\\d]{10}$").matcher(str).find();
    }
}
