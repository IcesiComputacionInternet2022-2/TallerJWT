package com.icesi.edu.users.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    CODE_01("No se encuentra autorizado"),
    CODE_02("Error en la digitacion de la entrada"),
    CODE_03("El email no es valido"),
    CODE_04("Contraseña incorrecta");


    private String message;
}