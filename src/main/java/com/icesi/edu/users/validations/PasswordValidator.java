package com.icesi.edu.users.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.ValidPassword, String> {
    @Override
    public void initialize(CustomAnnotations.ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        String reg = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W])(?!.*[\\s])";
        return Pattern.compile(reg).matcher(password).find();
    }
}
