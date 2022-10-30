package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_01("An authorization token is required"),
    CODE_02("Error verifying JWT token: "),
    CODE_03("Not authorized to view this information");

    private String message;
}