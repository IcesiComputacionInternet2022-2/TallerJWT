package com.icesi.edu.users.error.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    CODE_01("Incorrect attributes format and/or values"),
    CODE_02("Repeated email or phone number");

    final String message;

}
