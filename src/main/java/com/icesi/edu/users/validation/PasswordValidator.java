package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        //Regex for one upper case letter, one lower case letter, one number and one special character.
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$%@])[A-Za-z\\d#$%@]+$";
        return s.matches(regex);
    }
}
