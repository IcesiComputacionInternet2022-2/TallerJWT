package com.icesi.edu.users.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    CODE_UD_01("debe estar authenticado para realizar este request"),
    CODE_UD_02("token invalido");

    private String message;
}