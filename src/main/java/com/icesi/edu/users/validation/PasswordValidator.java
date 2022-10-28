package com.icesi.edu.users.validation;

import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.icesi.edu.users.constants.UserErrorCode.CODE_001;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W])(?!.*[\\s])")){
            return true;
        }
        //throw new UserDemoException(HttpStatus.INTERNAL_SERVER_ERROR,new UserDemoError(CODE_001.toString(), CODE_001.getMessage()));
        return false;
    }
}
