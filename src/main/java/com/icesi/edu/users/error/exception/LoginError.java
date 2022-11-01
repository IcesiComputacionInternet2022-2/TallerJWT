package com.icesi.edu.users.error.exception;

import com.icesi.edu.users.constant.LoginErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginError {

    private LoginErrorCode code;
    private String message;

}
