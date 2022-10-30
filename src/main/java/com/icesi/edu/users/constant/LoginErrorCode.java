package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginErrorCode {

    CODE_01("Unauthorized: You must be authenticated to make this request.");

    private final String message;
}
