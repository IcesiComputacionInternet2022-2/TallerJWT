package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.ErrorConstants;
import com.icesi.edu.users.constant.UserDemoErrorCode;
import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.LoginService;
import com.icesi.edu.users.utils.JWTParser;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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
                .orElseThrow(() -> new UserDemoException(HttpStatus.BAD_REQUEST,
                        new UserDemoError(UserDemoErrorCode.CODE_LOGIN_01, UserDemoErrorCode.CODE_LOGIN_01.getMessage())));

        if(user.getPassword().equals(loginDTO.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("userId", user.getId().toString());
            return new TokenDTO(JWTParser.createJWT(user.getId().toString(),user.getFirstName(), user.getFirstName(), claims,100000L));
        }
        throw new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserDemoErrorCode.CODE_AUTH_01, UserDemoErrorCode.CODE_AUTH_01.getMessage()));

    }

}