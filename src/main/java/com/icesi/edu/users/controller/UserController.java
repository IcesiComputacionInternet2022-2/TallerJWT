package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.icesi.edu.users.error.constants.ErrorCode.CODE_01;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {


    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        if(verifyFirstName(userCreateDTO) && verifyLastName(userCreateDTO) && verifyContactInfo(userCreateDTO)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userCreateDTO)));
        }
        else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(CODE_01,CODE_01.getMessage()));
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean verifyFirstName(UserCreateDTO userDTO){

        String firstName = userDTO.getFirstName();

        return firstName.length() <= 120 &&  firstName.length() > 0 && firstName.matches("^[a-zA-Z]*$");
    }

    private boolean verifyLastName(UserCreateDTO userDTO){

        String lastName = userDTO.getLastName();

        return lastName.length() <= 120 &&  lastName.length() > 0 && lastName.matches("^[a-zA-Z]*$");
    }

    private boolean verifyContactInfo(UserCreateDTO userDTO){

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
    private boolean verifyEmail(UserCreateDTO userDTO){

        String[] splitEmail = userDTO.getEmail().split("@");

        return splitEmail.length == 2 && splitEmail[1].equals("icesi.edu.co") && splitEmail[0].matches("^[a-zA-Z0-9]*$");
    }

    private boolean verifyPhoneNumber(UserCreateDTO userDTO){

        String phoneNumber = userDTO.getPhoneNumber();
        //String matcher also checks for spaces
        return phoneNumber.length() == 13 && phoneNumber.charAt(0) == '+' && phoneNumber.startsWith("57", 1) && phoneNumber.substring(1,13).matches("^[0-9]*$");
    }
}
