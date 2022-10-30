package com.icesi.edu.users.validation;

import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.icesi.edu.users.constant.UserErrorCode.C109;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.NameValidation, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        String passwordPatter = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#$%@])$";

        if(password.matches(passwordPatter) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C109, C109.getErrorMessage()));
        }
        return true;
    }
}
