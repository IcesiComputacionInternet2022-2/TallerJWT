package com.icesi.edu.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserDemoErrorCode {

    CODE_UD_01(ErrorConstants.CODE_UD_01),
    CODE_UD_02(ErrorConstants.CODE_UD_02),
    CODE_UD_03(ErrorConstants.CODE_UD_03),
    CODE_UD_04(ErrorConstants.CODE_UD_04),
    CODE_UD_05(ErrorConstants.CODE_UD_05),
    CODE_UD_06(ErrorConstants.CODE_UD_06),
    CODE_UD_07(ErrorConstants.CODE_UD_07),
    CODE_UD_08(ErrorConstants.CODE_UD_08),
    CODE_UD_09(ErrorConstants.CODE_UD_09),
    CODE_UD_10(ErrorConstants.CODE_UD_10),
    CODE_UD_11(ErrorConstants.CODE_UD_11),

    CODE_LOGIN_01(ErrorConstants.CODE_LOGIN_01),
    CODE_LOGIN_02(ErrorConstants.CODE_LOGIN_02),

    CODE_AUTH_01(ErrorConstants.CODE_AUTH_01),
    CODE_AUTH_02(ErrorConstants.CODE_AUTH_02);
    
    private String message;
}
