package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserDemoErrorCode {

    CODE_01("UNAUTHORIZED"),
    CODE_02("UNAUTHORIZED - You can't get the complete info about other users"),
    CODE_03("ANNOTATION ERROR"),
    CODE_04("UNAUTHORIZED - Invalid login"),
    CODE_05("The email is invalid"),
    CODE_06("The domain is wrong"),
    CODE_07("The phone number must have the colombian prefix"),
    CODE_08("The phone number is not valid"),
    CODE_09("Either email or phone number must be present"),
    CODE_10("The firstname and the lastname should not have more than 120 characters"),
    CODE_11("The firstname and the lastname should not have special characters or numbers"),
    CODE_12("The email is already in use"),
    CODE_13("The phone number is already in use");

    private final String message;
}
