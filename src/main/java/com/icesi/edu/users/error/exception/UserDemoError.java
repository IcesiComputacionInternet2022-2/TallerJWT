package com.icesi.edu.users.error.exception;

import com.icesi.edu.users.error.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDemoError {

    private ErrorCode code;
    private String message;
}
