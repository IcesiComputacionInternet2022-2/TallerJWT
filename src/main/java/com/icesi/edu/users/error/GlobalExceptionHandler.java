package com.icesi.edu.users.error;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleException(UserDemoException userDemoException) {
        return new ResponseEntity<>(userDemoException.getError(), userDemoException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleException(MethodArgumentNotValidException methodArgumentNotValidException) {
        UserDemoException tatabroException = new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("08", Objects.requireNonNull(methodArgumentNotValidException.getFieldError()).getDefaultMessage()));
        return new ResponseEntity<>(tatabroException.getError(), tatabroException.getHttpStatus());
    }
}