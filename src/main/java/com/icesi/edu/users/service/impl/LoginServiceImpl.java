package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.error.exception.InvalidLoginException;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.LoginService;
import com.icesi.edu.users.utils.JWTParser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    public final UserRepository repository;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {

        User user = StreamSupport.stream(repository.findAll().spliterator(),false).filter(user1 -> user1.
                getEmail().equals(loginDTO.getEmail())).
                findFirst().orElseThrow();


        if (user.getPassword().equals(loginDTO.getPassword())){
            Map<String,String> claims = new HashMap<>();
            claims.put("userId",user.getId().toString());
            return new TokenDTO(JWTParser.createJWT(user.getId().toString(),user.getFirstName(),user.getLastName(),claims,300000));
        }

        throw new UserDemoException(HttpStatus.BAD_REQUEST,new UserDemoError(403,"Invalid credentials please try again"));
    }
}
