package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.LoginErrorCode;
import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.exception.LoginError;
import com.icesi.edu.users.exception.LoginException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.LoginService;
import com.icesi.edu.users.utils.JWTParser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    public final UserRepository userRepository;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        User user = StreamSupport.stream(userRepository.findAll().spliterator(),false)
                .filter(user1 -> user1.getEmail().equals(loginDTO.getEmail()))
                .findFirst()
                .orElseThrow(() -> new LoginException(HttpStatus.UNAUTHORIZED, new LoginError(LoginErrorCode.CODE_01, LoginErrorCode.CODE_01.getMessage())));
        if(user.getPassword().equals(loginDTO.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("userId", user.getId().toString());
            System.out.println(JWTParser.createJWT(user.getId().toString(),user.getFirstName(), user.getFirstName(), claims,100000L));
            return new TokenDTO(JWTParser.createJWT(user.getId().toString(),user.getFirstName(), user.getFirstName(), claims,100000L));
        }
        throw new LoginException(HttpStatus.UNAUTHORIZED, new LoginError(LoginErrorCode.CODE_01, LoginErrorCode.CODE_01.getMessage()));
    }

}
