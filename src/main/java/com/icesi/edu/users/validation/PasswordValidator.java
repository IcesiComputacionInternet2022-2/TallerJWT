package com.icesi.edu.users.validation;

import com.icesi.edu.users.error.UserErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {


    @Override
    public void initialize(CustomAnnotations.PasswordValidation passwordValidation) {   }

    @Override
    @SneakyThrows
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
/*
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*., ?]).+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);*/
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#$%@]).+$";

        boolean result = str.matches(regex);
/*
        if(!result){
            System.out.println("Mala contraseña");
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserErrorCode.CODE_01,"The password is not strong"));
        }
        System.out.println("Buena contraseña");*/

        return result;
    }
}