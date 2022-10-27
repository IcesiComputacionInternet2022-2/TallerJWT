package com.icesi.edu.users.validation;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {

    @Override
    public void initialize(CustomAnnotations.PasswordValidation passwordValidation) {
    }

    @Override
    @SneakyThrows
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid =  Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W])(?!.*[\\s])").matcher(str).find();
        if(!valid){
            throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, new UserError(UserErrorCode.CODE_01, UserErrorCode.CODE_01.getMessage()));
        }
        return valid;
    }
}
