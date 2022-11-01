package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserDemoErrorCode {

    CODE_UD_01("Must be authenticated to access this resource");

    private String message;
}
