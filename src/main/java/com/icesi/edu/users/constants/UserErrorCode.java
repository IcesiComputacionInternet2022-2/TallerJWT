package com.icesi.edu.users.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_401("Debe estar authenticado para realizar este request"),
    CODE_001("La contraseña debe tener al menos una letra mayuscula, una minuscula, un numero y un simbolo como #$%@");

    private String message;

    public String getMessage(){
        return message;
    }
}