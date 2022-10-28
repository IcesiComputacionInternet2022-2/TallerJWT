package com.icesi.edu.users.service.impl;

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
import java.util.stream.StreamSupport;

import static com.icesi.edu.users.error.constants.ErrorCode.CODE_04;
import static com.icesi.edu.users.error.constants.ErrorCode.CODE_05;

@AllArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {


    private final UserRepository userRepository;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        User user = StreamSupport.stream(userRepository.findAll().spliterator(),false)
                .filter(user1 -> user1.getEmail().equals(loginDTO.getEmail()))
                .findFirst()
                .orElseThrow(() -> new UserDemoException(HttpStatus.NOT_FOUND,new UserDemoError(CODE_04,CODE_04.getMessage())));
        if(user.getPassword().equals(loginDTO.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("userId", user.getId().toString());
            return new TokenDTO(JWTParser.createJWT(user.getId().toString(),user.getFirstName(), user.getFirstName(), claims,100000L));
        }
        throw new UserDemoException(HttpStatus.FORBIDDEN,new UserDemoError(CODE_05,CODE_05.getMessage()));
    }
}
