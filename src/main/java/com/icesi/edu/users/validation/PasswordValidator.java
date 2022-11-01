package com.icesi.edu.users.validation;

import com.icesi.edu.users.constant.LoginErrorCode;
import com.icesi.edu.users.error.exception.LoginError;
import com.icesi.edu.users.error.exception.LoginException;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        boolean passwordMatch = password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%])(?=\\S+$).{8,}$");

        if (!passwordMatch) {
            LoginError loginError = new LoginError(LoginErrorCode.CODE_01, LoginErrorCode.CODE_01.getMessage());
            throw new LoginException(HttpStatus.BAD_REQUEST, loginError);
        }

        return true;
    }
}
