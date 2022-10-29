package com.icesi.edu.users.validation;

import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import org.passay.*;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordAnnotation implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {


    @Override
    public void initialize(CustomAnnotations.PasswordValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PasswordValidator passwordValidator = new PasswordValidator(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );
        RuleResult ruleResult = passwordValidator.validate(new PasswordData(value));
        if(ruleResult.isValid()){
            return true;
        }else{
            throw new UserDemoException(HttpStatus.NOT_ACCEPTABLE, new UserDemoError("406", "Invalid Password: Must have at least: 1 Uppercase, 1 Lowercase, 1 Number and a special symbol such as #$%@"));
        }
    }
}
