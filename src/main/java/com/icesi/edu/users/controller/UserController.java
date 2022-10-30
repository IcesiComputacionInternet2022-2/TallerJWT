package com.icesi.edu.users.controller;

import com.google.common.hash.Hashing;
import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserPublicDTO;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.utils.JWTParser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        UserDTO user = userMapper.fromUser(userService.getUser(userId));
        return user;
    }

    @Override
    public UserDTO createUser(@Valid UserDTO userDTO) {
        if(hasAtLeastOneContactWay(userDTO.getEmail(), userDTO.getPhoneNumber())){
            userDTO.setPassword(Hashing.sha256().hashString(userDTO.getPassword(), StandardCharsets.UTF_8).toString());
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }
        throw new UserException(HttpStatus.BAD_REQUEST, new UserError(UserErrorCode.CODE_10, UserErrorCode.CODE_10.getMessage()));
    }

    @Override
    public List<UserPublicDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromPublicUser).collect(Collectors.toList());
    }


    public boolean hasAtLeastOneContactWay(String email, String number){
        return ((email != null) && !email.isBlank()) || ((number != null) && !number.isBlank() );
    }
}
