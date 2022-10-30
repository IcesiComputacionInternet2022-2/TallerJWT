package com.icesi.edu.users.service;

import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginService {
    public TokenDTO login(@RequestBody LoginDTO loginDTO);

}
