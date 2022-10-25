package com.icesi.edu.users.error.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserDemoException extends Throwable {

    private HttpStatus httpStatus;
    private UserDemoError error;
}
