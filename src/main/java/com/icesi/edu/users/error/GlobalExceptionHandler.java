package com.icesi.edu.users.error;

import com.icesi.edu.users.constants.UserErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.util.Map;
import java.util.Objects;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleException(UserDemoException userDemoException){
        return new ResponseEntity<>(userDemoException.getError(), userDemoException.getHttpStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<UserDemoError> handleArgumentException(MethodArgumentNotValidException exception){
        BindingResult binding = exception.getBindingResult();

        String wrongField = Objects.requireNonNull(binding.getFieldError()).getField();

        UserErrorCode errorCode = getErrorCode(wrongField);


        HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

        UserDemoError userError = new UserDemoError(errorCode.toString(), errorCode.getMessage());
        return new ResponseEntity<>(userError, errorStatus);
    }

    private UserErrorCode getErrorCode(String wrongField){
        switch (wrongField){
            case "password":
                return UserErrorCode.CODE_401;
        }
        //I'm not going to implement the other validations... (cause they're not mandatory)
        return null;
    }

}
