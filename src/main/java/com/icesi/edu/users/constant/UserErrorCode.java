package com.icesi.edu.users.constant;

public class UserErrorCode {

    public static final String CODE_UD_01 = "At least one of the email or phone number fields must be entered.";
    public static final String CODE_UD_02 = "The email must begin with alphanumeric characters (no special characters) followed by the @icesi.edu.co domain.";
    public static final String CODE_UD_03 = "The phone number must begin with the +57 prefix followed by 10 digits, and mustn't have whitespaces.";
    public static final String CODE_UD_04 = "The first name and last name mustn't contain more than 120 characters, nor special characters or numbers.";
    public static final String CODE_UD_05 = "The email and/or phone number is already registered.";
    public static final String CODE_UD_06 = "Password must have at least 1 upper case letter, 1 lower case letter, a number and a symbol like #$%@";
    public static final String CODE_UD_07 = "You must be authenticated to perform this request.";
    public static final String CODE_UD_08 = "You can only see your own data.";
}