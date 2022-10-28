package com.icesi.edu.users.error;

import com.icesi.edu.users.constants.ErrorConstants;
import com.icesi.edu.users.error.exceptions.UserDemoError;
import com.icesi.edu.users.error.exceptions.UserDemoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserDemoError> handleException(UserDemoException userDemoException) {
        return new ResponseEntity<>(userDemoException.getError(), userDemoException.getHttpStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String bindingRes = exception.getBindingResult().toString();
        Map<String, Object> body = new HashMap<>();
        UserDemoError error = new UserDemoError(ErrorConstants.ERR_Nx00.name(), ErrorConstants.ERR_Nx00.getMessage());
        body.put("error", error);
        body.put("binding", bindingRes);
        return new ResponseEntity<>(body, HttpStatus.I_AM_A_TEAPOT);
    }

}
