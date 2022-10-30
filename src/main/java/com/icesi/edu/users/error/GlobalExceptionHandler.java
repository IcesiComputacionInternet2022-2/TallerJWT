package com.icesi.edu.users.error;

import com.icesi.edu.users.constants.ErrorConstants;
import com.icesi.edu.users.error.exceptions.UserDemoError;
import com.icesi.edu.users.error.exceptions.UserDemoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<UserDemoError> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        BindingResult bindingRes = exception.getBindingResult();

        String errorField = Objects.requireNonNull(bindingRes.getFieldError()).getField();

        String[] body = resolveErrorCode(errorField);

        String errorName = (String) body[0];
        String errorMessage = (String) body[1];
        HttpStatus status = HttpStatus.BAD_REQUEST;

        UserDemoError error = new UserDemoError(errorName, errorMessage);
        return new ResponseEntity<>(error, status);
    }

    private String[] resolveErrorCode(String errorField) {
        String errorName;
        String errorMessage;

        switch (errorField) {
            default:
                errorName = ErrorConstants.ERR_Nx00.name();
                errorMessage = ErrorConstants.ERR_Nx00.getMessage();
                break;
            case "email":
                errorName = ErrorConstants.ERR_0x01.name();
                errorMessage = ErrorConstants.ERR_0x01.getMessage();
                break;
            case "phoneNumber":
                errorName = ErrorConstants.ERR_0x02.name();
                errorMessage = ErrorConstants.ERR_0x02.getMessage();
                break;
            case "firstName":
                errorName = ErrorConstants.ERR_0x03_1.name();
                errorMessage = ErrorConstants.ERR_0x03_1.getMessage();
                break;
            case "lastName":
                errorName = ErrorConstants.ERR_0x03_2.name();
                errorMessage = ErrorConstants.ERR_0x03_2.getMessage();
                break;
            case "password":
                errorName = ErrorConstants.ERR_0x04.name();
                errorMessage = ErrorConstants.ERR_0x04.getMessage();
                break;
        }

        return new String[]{ errorName, errorMessage };
    }

}
