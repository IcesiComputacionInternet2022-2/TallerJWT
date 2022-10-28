package com.icesi.edu.users.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.ValidPassword, String> {
    @Override
    public void initialize(CustomAnnotations.ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        String re = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W])(?!.*[\\s])";
        return password.matches(re);
    }
}
