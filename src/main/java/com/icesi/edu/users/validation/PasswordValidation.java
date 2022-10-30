package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidation implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("[A-Z]+[a-z]+[0-9]+[#$%@]+");
    }
}
