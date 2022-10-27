package com.icesi.edu.users.error;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import com.icesi.edu.users.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;
import java.net.BindException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserError> handleException(UserException userException){
        return new ResponseEntity<>(userException.getError(), userException.getHttpStatus());
    }

    //O BindException
    @ExceptionHandler
    public ResponseEntity<UserError> handleException(BindException exception){
        UserError userError = new UserError(UserErrorCode.CODE_01, UserErrorCode.CODE_01.getMessage());
        return new ResponseEntity<>(userError, HttpStatus.valueOf(exception.getMessage()));
    }




}
