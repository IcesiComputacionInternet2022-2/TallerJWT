package com.icesi.edu.users.error.exception;

import com.icesi.edu.users.constants.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDemoError {
    private ErrorCodes code;
    private String message;
}
