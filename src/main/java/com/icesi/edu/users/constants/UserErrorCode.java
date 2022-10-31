package com.icesi.edu.users.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_01("UNAUTHORIZED"),
    CODE_02("Email is already registered"),
    CODE_03("This phone number is already registered"),
    CODE_04("Either email or Phone number should be filled");

    private final String message;

}
