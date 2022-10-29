package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<CustomAnnotations.NameValidation, String> {

    @Override
    public void initialize(CustomAnnotations.NameValidation nameValidation) {    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        return !Pattern.compile("[^a-zA-z ]").matcher(str).find();
    }
}