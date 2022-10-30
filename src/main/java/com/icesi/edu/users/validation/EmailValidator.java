package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<CustomAnnotations.EmailValidation, String> {

    @Override
    public boolean isValid(String domain, ConstraintValidatorContext constraintValidatorContext) {
        return domain.matches("\\w+@icesi.edu.co$");
    }
}