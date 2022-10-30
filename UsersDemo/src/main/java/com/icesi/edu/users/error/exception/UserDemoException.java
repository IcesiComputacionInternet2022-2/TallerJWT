package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class UserDemoException extends RuntimeException {

    private HttpStatus httpStatus;
    private UserDemoError error;
}
