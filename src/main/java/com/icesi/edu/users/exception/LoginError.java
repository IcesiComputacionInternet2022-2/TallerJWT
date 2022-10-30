package com.icesi.edu.users.exception;

import com.icesi.edu.users.constant.LoginErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginError {

    private LoginErrorCode loginErrorCode;
    private String message;
}
