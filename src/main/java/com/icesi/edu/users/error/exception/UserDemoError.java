package com.icesi.edu.users.error.exception;

import com.icesi.edu.users.constant.UserDemoErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserDemoError {

    private UserDemoErrorCode code;
    private String message;

}
