package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constants.UserErrorCode;
import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.LoginService;
import com.icesi.edu.users.utils.JWTParser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    public final UserRepository userRepository;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserErrorCode.CODE_01, UserErrorCode.CODE_01.getMessage())));
        if (user.getPassword().equals(loginDTO.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("userId", user.getId().toString());
            return new TokenDTO(JWTParser.createJWT(user.getId().toString(), user.getFirstName(), user.getFirstName(), claims, 1000000L));
        }
        throw new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserErrorCode.CODE_01, UserErrorCode.CODE_01.getMessage()));
    }

}
