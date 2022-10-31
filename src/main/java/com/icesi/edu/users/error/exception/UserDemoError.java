package com.icesi.edu.users.error.exception;

import com.icesi.edu.users.constants.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDemoError {
    private UserErrorCode code;
    private String message;
}
