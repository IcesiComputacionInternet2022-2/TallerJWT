package com.icesi.edu.users.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<CustomAnnotations.ValidName, String> {
    @Override
    public void initialize(CustomAnnotations.ValidName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        int nameLen = name.length();
        int CAP = 120;
        String pattern = "[a-zA-Z]+";
        return nameLen <= CAP && name.matches(pattern);
    }
}
