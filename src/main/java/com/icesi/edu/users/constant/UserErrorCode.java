package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {
    C101("Email or Phone field has to be filled"),
    C102("Email domain not valid"),
    C103("Email username not valid"),
    C104("Phone number invalid"),
    C105("First name is empty or exceeds limit size (120 characters)"),
    C106("Last name is empty or exceeds limit size (120 characters)"),
    C107("First name can't contain special characters nor numbers"),
    C108("Last name can't contain special characters nor numbers"),
    C109("Invalid password. It must include upper case letter, lower case letter, a number and a symbol like #$%@. Please try again"),
    S101("Your credentials don't allow to send the request for this user due to sensitive data"),
    E401("Must be authenticated to send this request");

    private final String errorMessage;
}
