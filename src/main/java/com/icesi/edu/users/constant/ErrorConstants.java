package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorConstants {

    CODE_01("401","Unahuthorized: error debe estar autenticado para realizar el request"),
    CODE_02("002","You cannot consult another user's information"),
    CODE_03("003","The email or password are null"),
    CODE_04("004","The domain is incorrect"),
    CODE_05("005","The email can only have letters"),
    CODE_06("006","The prefix of the number is not correct"),
    CODE_07("007","The number can only have numbers"),
    CODE_08("008","The phone must have 10 numbers in addition to the prefix"),
    CODE_09("009","The full name can be only 120 characters"),
    CODE_10("010","The first and last name can only contain letters"),

    CODE_11("011","The password must contain at least \n- a capital letter \n- a lower case \n- a number \n- one of the following characters #$%@");


    private String code;
    private String message;
    //public static final String CODE_UD_01 = "EMAIL OR NUMBER IS REQUIRED";

}
