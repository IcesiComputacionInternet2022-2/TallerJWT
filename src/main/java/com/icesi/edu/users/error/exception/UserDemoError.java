package com.icesi.edu.users.error.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDemoError {
    private int code;
    private String message;
}