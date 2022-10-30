package com.icesi.edu.users.error.exception;

import com.icesi.edu.users.constant.UserDemoErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDemoError {

    private UserDemoErrorCode code;
    private String message;
}
