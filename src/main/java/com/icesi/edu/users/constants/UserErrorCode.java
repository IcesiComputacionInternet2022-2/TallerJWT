package com.icesi.edu.users.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_401("Debe estar authenticado para realizar este request");

    private String message;

    public String getMessage(){
        return message;
    }
}