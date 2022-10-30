package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class UserException extends RuntimeException{
    private HttpStatus httpStatus;
    private UserError error;
}
