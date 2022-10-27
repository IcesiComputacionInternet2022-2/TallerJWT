package com.icesi.edu.users.service;

import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;

public interface LoginService {
    TokenDTO login(LoginDTO loginDTO);
}
