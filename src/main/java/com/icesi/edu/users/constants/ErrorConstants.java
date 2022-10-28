package com.icesi.edu.users.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorConstants {

    ERR_Nx00("You're a teapot (this is a test error code)."),
    ERR_0x01("The given email has an invalid format. Try again."),
    ERR_0x02("The given phone number has an invalid format. Try again."),
    ERR_0x03_1("The given name has an invalid format. Try again."),
    ERR_0x03_2("The given last name has an invalid format. Try again."),
    ERR_0x04("Password needs to have at least one capital, one lowercase, one number and one symbol. Try again."),
    ERR_1x01("Wrong credentials provided. Try again."),
    ERR_1x02("Requested user is not currently logged in. Try again.");

    private final String message;
}
