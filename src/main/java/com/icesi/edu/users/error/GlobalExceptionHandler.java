package com.icesi.edu.users.error;

import com.icesi.edu.users.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<UserError> handleException(UserException userException){
        return new ResponseEntity<>(userException.getUserError(),
                userException.getHttpStatus());
    }

    @ExceptionHandler({CustomValidationException.class})
    @ResponseBody
    public ResponseEntity<UserError> handleException(CustomValidationException customValidationException){
        return new ResponseEntity<>(customValidationException.getUserError(),
                customValidationException.getHttpStatus());
    }

    @ExceptionHandler({LoginException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<LoginError> handleLoginException(LoginException loginException){
        return new ResponseEntity<>(loginException.getLoginError(),
                loginException.getHttpStatus());
    }
}
