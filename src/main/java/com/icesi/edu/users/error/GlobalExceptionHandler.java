package com.icesi.edu.users.error;

import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserError> handleException(UserException userDemoException){
        return new ResponseEntity<>(userDemoException.getError(),userDemoException.getHttpStatus());
    }
}
