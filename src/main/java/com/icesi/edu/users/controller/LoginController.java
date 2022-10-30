package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.LoginAPI;
import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LoginController implements LoginAPI {

    private LoginService service;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        return service.login(loginDTO);
    }
}
