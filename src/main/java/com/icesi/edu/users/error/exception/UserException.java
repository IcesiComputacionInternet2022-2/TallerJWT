package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class UserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;
    private UserError error;

}
