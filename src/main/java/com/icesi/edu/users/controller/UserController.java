package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.exception.UserError;
import com.icesi.edu.users.exception.UserException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    static final String DOMAIN = "@icesi.edu.co";
    static final String PREFIX = "+57";
    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        UserDTO userDTO = userMapper.fromUser(userService.getUser(userId));
        if(userDTO!=null)
            userDTO.setLocalDate(LocalDate.now());
        return userDTO;
    }

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        validateUserEmail(userCreateDTO);
        validateUserPhoneNumber(userCreateDTO);
        validateFirstName(userCreateDTO);
        validateLastName(userCreateDTO);
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userCreateDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private void validateUserEmail(UserCreateDTO userCreateDTO){
        String email = userCreateDTO.getEmail();
        if(email != null && email.toLowerCase().endsWith(DOMAIN) &&
                email.substring(0, email.length()-13).matches("[a-zA-Z0-9]+"))
            return;
        throw new UserException(HttpStatus.BAD_REQUEST, new UserError(UserErrorCode.CODE_01, UserErrorCode.CODE_01.getMessage()));
    }

    private void validateUserPhoneNumber(UserCreateDTO userCreateDTO){
        String phoneNumber = userCreateDTO.getPhoneNumber();
        if(phoneNumber != null && phoneNumber.length() == 13 &&
                phoneNumber.startsWith(PREFIX) && phoneNumber.substring(1).matches("[0-9]+"))
            return;
        throw new UserException(HttpStatus.BAD_REQUEST, new UserError(UserErrorCode.CODE_02, UserErrorCode.CODE_02.getMessage()));
    }

    private void validateFirstName(UserCreateDTO userCreateDTO){
        String firstName = userCreateDTO.getFirstName();
        if(firstName != null && firstName.length() <= 120 && firstName.matches("[a-zA-Z]+"))
            return;
        throw new UserException(HttpStatus.BAD_REQUEST, new UserError(UserErrorCode.CODE_03, UserErrorCode.CODE_03.getMessage()));
    }

    private void validateLastName(UserCreateDTO userCreateDTO){
        String lastName = userCreateDTO.getLastName();
        if(lastName != null && lastName.length() <= 120 && lastName.matches("[a-zA-Z]+"))
            return;
        throw new UserException(HttpStatus.BAD_REQUEST, new UserError(UserErrorCode.CODE_04, UserErrorCode.CODE_04.getMessage()));
    }
}
