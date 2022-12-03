package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<CustomAnnotations.ValidPhoneNumber, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String nameRegex = "^\\+[1-9][1-9][\\s\\S]*";

        return s.matches(nameRegex);
    }
}
