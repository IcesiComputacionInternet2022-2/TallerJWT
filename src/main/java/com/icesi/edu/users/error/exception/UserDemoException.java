package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserDemoException extends RuntimeException{
    private HttpStatus httpStatus;
    private UserDemoError error;
}
