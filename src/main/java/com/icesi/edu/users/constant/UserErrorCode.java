package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_01("The password doesn't fulfill the character diversity"),
    CODE_02("Unauthorized. Authentication needed to do this request.");

    private final String message;
}