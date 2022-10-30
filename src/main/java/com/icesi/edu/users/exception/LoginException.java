package com.icesi.edu.users.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.security.InvalidParameterException;

@Getter
@Setter
@AllArgsConstructor
public class LoginException extends InvalidParameterException {

    private HttpStatus httpStatus;
    private LoginError loginError;
}