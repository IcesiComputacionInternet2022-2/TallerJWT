package com.icesi.edu.users.error;

import com.icesi.edu.users.constant.UserDemoErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleException(UserDemoException userDemoException){
        return new ResponseEntity<>(userDemoException.getError(), userDemoException.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserDemoError> handleInvalidArgumentAnnotation(MethodArgumentNotValidException exception){
        UserDemoException userDemoException = new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_UD_10, UserDemoErrorCode.CODE_UD_10.getMessage()));
        return new ResponseEntity<>(userDemoException.getError(), userDemoException.getHttpStatus());
    }


    /*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(exception.)
        exception.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(exception.getFieldError(), HttpStatus.BAD_REQUEST);
    }
    */
}
