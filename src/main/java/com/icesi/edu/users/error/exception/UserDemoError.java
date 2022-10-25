package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDemoError {
    private String code;
    private String message;
}
