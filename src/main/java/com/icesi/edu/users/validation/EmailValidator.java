package com.icesi.edu.users.validation;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<CustomAnnotations.EmailValidation, String> {

    @Override
    public void initialize(CustomAnnotations.EmailValidation passwordValidation) {    }

    @Override
    @SneakyThrows
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = Pattern.compile(".*(@ICESI\\.EDU\\.CO)$|.*(@icesi\\.edu\\.co)$").matcher(str).find();
        return valid;
    }
}