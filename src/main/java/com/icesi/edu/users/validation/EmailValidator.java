package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<CustomAnnotations.ValidEmail, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        String emailValidationRegex = "[A-Za-z\\d]+@[A-Za-z\\d]+\\.[A-Za-z]+(.[A-Za-z]+)?";

        return s.matches(emailValidationRegex);
    }
}
