package com.icesi.edu.users.dto;

import com.icesi.edu.users.error.exception.LoginException;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class LoginDTO {

    private String email;
    private String password;

}
