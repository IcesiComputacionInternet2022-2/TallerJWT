package com.icesi.edu.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<CustomAnnotations.ValidUsername, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    EmailValidator emailValidator = new EmailValidator();
    PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
    return emailValidator.isValid(s, constraintValidatorContext) || phoneNumberValidator.isValid(s,constraintValidatorContext);
    }
}
