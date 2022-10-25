package com.icesi.edu.users.error;

import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleException(UserDemoException userDemoException){
        return new ResponseEntity<>(userDemoException.getError(),userDemoException.getHttpStatus());
    }
}
