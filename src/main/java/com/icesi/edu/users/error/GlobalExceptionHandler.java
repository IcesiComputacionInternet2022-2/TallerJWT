package com.icesi.edu.users.error;
import com.icesi.edu.users.error.exception.LoginError;
import com.icesi.edu.users.error.exception.LoginException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<LoginError> handleException(LoginException loginException){
        return new ResponseEntity<LoginError>(loginException.getError(), loginException.getHttpStatus());
    }


}
