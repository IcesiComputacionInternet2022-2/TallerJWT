package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_01("Invalid email address."),
    CODE_02("Invalid phone number."),
    CODE_03("Invalid first name."),
    CODE_04("Invalid last name."),
    CODE_05("Email or phone number already exist."),
    CODE_06("Invalid password: ");

    private final String message;
}
