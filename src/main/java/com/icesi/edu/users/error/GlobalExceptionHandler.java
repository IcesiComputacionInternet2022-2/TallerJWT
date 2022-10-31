package com.icesi.edu.users.error;

import com.icesi.edu.users.constant.UserDemoErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleException(UserDemoException userDemoException){
        return new ResponseEntity<>(userDemoException.getError(),userDemoException.getHttpStatus());
    }
    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleAnnotationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        UserDemoException UserDemoException = new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_03, Objects.requireNonNull(methodArgumentNotValidException.getFieldError()).getDefaultMessage()));
        return new ResponseEntity<>(UserDemoException.getError(), UserDemoException.getHttpStatus());
    }
    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleConstraintException(ConstraintViolationException constraintViolationException) {
        UserDemoException UserDemoException = new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_03, Objects.requireNonNull(constraintViolationException.getConstraintViolations().stream().reduce("",(s, constraintViolation) ->  constraintViolation.getMessage(), (s, s2) -> s + s2))));
        return new ResponseEntity<>(UserDemoException.getError(), UserDemoException.getHttpStatus());
    }
}
