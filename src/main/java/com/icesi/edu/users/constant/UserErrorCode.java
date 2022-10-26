package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_01("Hello world"),
    CODE_02("The password doesn't fulfill the character diversity");

    private final String message;
}