package com.icesi.edu.users.service.impl;

import com.google.common.hash.Hashing;
import com.icesi.edu.users.constants.ErrorConstants;
import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.error.exceptions.UserDemoError;
import com.icesi.edu.users.error.exceptions.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.LoginService;
import com.icesi.edu.users.utils.JWTParser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    public final UserRepository repository;
    @Override
    public TokenDTO login(@Valid LoginDTO loginDTO) {
        User user = StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(u -> u.getEmail().equals(loginDTO.getEmail()))
                .findFirst()
                .orElseThrow();
        return authenticatePassword(user, loginDTO);
    }

    @SneakyThrows
    private TokenDTO authenticatePassword(User user, LoginDTO loginDTO) {
        String expectedHash = user.getHashed();
        String requestHash = Hashing.sha256().hashString(loginDTO.getPassword(), StandardCharsets.UTF_8).toString();

        if (requestHash.equals(expectedHash)) {
            Map<String, String> claims = new HashMap<>();
            claims.put("userId", user.getId().toString());
            return new TokenDTO(JWTParser.createJWT(user.getId().toString(), user.getFirstName(), user.getFirstName(), claims, 100000L));
        }

        throw new UserDemoException(HttpStatus.ACCEPTED, new UserDemoError(ErrorConstants.ERR_1x01.name(), ErrorConstants.ERR_1x01.getMessage()));
    }
}
