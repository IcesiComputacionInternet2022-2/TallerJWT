package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {

    @Override
    public void initialize(CustomAnnotations.PasswordValidation passwordValidation) {
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W])(?!.*[\\s])").matcher(str).find();
    }
}
