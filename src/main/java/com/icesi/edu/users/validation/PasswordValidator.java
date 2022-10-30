package com.icesi.edu.users.validation;

import com.icesi.edu.users.constant.ErrorConstants;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {

    private final String REGEX =  "^(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*[0-9]+)(?=.*[@#%$]+)[A-Za-z0-9#$%@]+$";


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(s + ":" + s.matches(REGEX));
        if(s.matches(REGEX)){
            return s.matches(REGEX);
        }else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_11.getCode(), ErrorConstants.CODE_11.getMessage()));
        }
    }
}
