package com.icesi.edu.users.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.dto.UserWithPasswordDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.utils.JWTParser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.icesi.edu.users.error.constants.ErrorCode.*;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {


    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserWithPasswordDTO getUser(UUID userId, TokenDTO authorization) {

        String requesterId = JWTParser.decodeJWT(authorization.getToken().replace("Bearer ", "")).get("userId",String.class);


        if(!requesterId.equals(userId.toString())){
            throw new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(CODE_06,CODE_06.getMessage()));
        }

        return userMapper.fromUserWithPassword(userService.getUser(userId));
    }

    @Override
    public UserWithPasswordDTO createUser(UserWithPasswordDTO userWithPasswordDTO) {
        verifyPassword(userWithPasswordDTO);

        if(verifyFirstName(userWithPasswordDTO) && verifyLastName(userWithPasswordDTO) && verifyContactInfo(userWithPasswordDTO)){
            return userMapper.fromUserWithPassword(userService.createUser(userMapper.fromDTO(userWithPasswordDTO)));
        }
        else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(CODE_01,CODE_01.getMessage()));
        }
    }

    private void verifyPassword(UserWithPasswordDTO userWithPasswordDTO){
        if(userWithPasswordDTO.getPassword().matches("^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$")){
            throw new UserDemoException(HttpStatus.BAD_REQUEST,new UserDemoError(CODE_03,CODE_03.getMessage()));
        }
    }


    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean verifyFirstName(UserWithPasswordDTO userDTO){

        String firstName = userDTO.getFirstName();

        return firstName.length() <= 120 &&  firstName.length() > 0 && firstName.matches("^[a-zA-Z]*$");
    }

    private boolean verifyLastName(UserWithPasswordDTO userDTO){

        String lastName = userDTO.getLastName();

        return lastName.length() <= 120 &&  lastName.length() > 0 && lastName.matches("^[a-zA-Z]*$");
    }

    private boolean verifyContactInfo(UserWithPasswordDTO userDTO){

        boolean result = false;

        if (userDTO.getEmail() != null){
            result = verifyEmail(userDTO);

            if(!result) return false;
        }

        if(userDTO.getPhoneNumber() != null){
            result = verifyPhoneNumber(userDTO);

            if(!result) return false;
        }

        return result;
    }
    private boolean verifyEmail(UserWithPasswordDTO userDTO){

        String[] splitEmail = userDTO.getEmail().split("@");

        return splitEmail.length == 2 && splitEmail[1].equals("icesi.edu.co") && splitEmail[0].matches("^[a-zA-Z0-9]*$");
    }

    private boolean verifyPhoneNumber(UserWithPasswordDTO userDTO){

        String phoneNumber = userDTO.getPhoneNumber();
        //String matcher also checks for spaces
        return phoneNumber.length() == 13 && phoneNumber.charAt(0) == '+' && phoneNumber.startsWith("57", 1) && phoneNumber.substring(1,13).matches("^[0-9]*$");
    }
}
