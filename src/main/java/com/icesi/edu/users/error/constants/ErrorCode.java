package com.icesi.edu.users.error.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    CODE_01("Incorrect attributes format and/or values"),
    CODE_02("Repeated email or phone number"),
    CODE_03("Password must have at least 8 characters, one number, one uppercase letter, one lower case letter and a symbol like $%&#"),
    CODE_04("Email not found"),
    CODE_05("Incorrect password"),
    CODE_06("Access denied, you cannot see confidential information about other users"),
    CODE_07("Missing or invalid token, authentication is required for this request");


    final String message;

}
