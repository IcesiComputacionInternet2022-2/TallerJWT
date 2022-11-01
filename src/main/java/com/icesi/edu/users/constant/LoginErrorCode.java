package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginErrorCode {

    CODE_01("Password must contain at least one uppercase letter, one lowercase letter, one number and one special character");

    private final String message;

}
