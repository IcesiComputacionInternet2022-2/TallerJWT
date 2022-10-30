package com.icesi.edu.users.validation;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.exception.CustomValidationException;
import com.icesi.edu.users.exception.UserError;
import lombok.SneakyThrows;
import org.passay.*;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CustomPasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {

    @Override
    public void initialize(CustomAnnotations.PasswordValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @SneakyThrows
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        ));
        RuleResult ruleResult = passwordValidator.validate(new PasswordData(value));
        List<String> messages = passwordValidator.getMessages(ruleResult);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        if (!ruleResult.isValid()) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST, new UserError(UserErrorCode.CODE_06, UserErrorCode.CODE_06.getMessage()+messageTemplate));
        }
        return ruleResult.isValid();
    }
}
