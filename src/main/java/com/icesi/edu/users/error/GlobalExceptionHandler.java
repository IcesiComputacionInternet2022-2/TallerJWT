package com.icesi.edu.users.error;

import com.icesi.edu.users.constants.ErrorCodes;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleException(UserDemoException userDemoException){
        return new ResponseEntity<>(userDemoException.getError(),userDemoException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleAnnotationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        UserDemoException userException = new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorCodes.CODE_UD_01, Objects.requireNonNull(methodArgumentNotValidException.getFieldError()).getDefaultMessage()));
        return new ResponseEntity<>(userException.getError(), userException.getHttpStatus());
    }
}
