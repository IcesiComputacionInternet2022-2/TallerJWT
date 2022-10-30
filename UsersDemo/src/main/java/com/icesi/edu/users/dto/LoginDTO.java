package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.CustomAnnotations.PasswordValidation;
import lombok.Data;

@Data
public class LoginDTO {

    private String email;

    @PasswordValidation
    private String password;

}
