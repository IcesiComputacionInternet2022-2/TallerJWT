package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.AssociationOverride;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_00("Restart your pc, we couldn't track the error."),
    CODE_01("The password doesn't fulfill the character diversity"),
    CODE_02("The email doesn't belong to the expected domain."),
    CODE_03("The phone doesn't follows the expected pattern."),
    CODE_04("The name can take only letters."),

    CODE_05("The last name can take only letters."),

    CODE_06("The email and phone number are not repeatable fields. Try with another value"),

    CODE_401("Unauthorized. Authentication needed to do this request.");

    private final String message;
}