package com.icesi.edu.users.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<CustomAnnotations.ValidEmailAndDomain, String> {

    @Override
    public void initialize(CustomAnnotations.ValidEmailAndDomain constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) return false;
        String domain = "^.*(@icesi.edu.co)$";
        int atCount = email.length() - email.replaceAll("@", "").length();
        int dotCount = email.length() - email.replaceAll("\\.", "").length();
        String pattern = "[^a-zA-z0-9\\-_]";
        return email.toLowerCase().matches(domain) && !email.matches(pattern) && atCount == 1 && dotCount == 2;
    }
}
