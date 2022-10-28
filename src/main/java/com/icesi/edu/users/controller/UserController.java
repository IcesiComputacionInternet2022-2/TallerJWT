package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.constant.UserDemoErrorCode;
import com.icesi.edu.users.dto.ResponseDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {


    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(@Valid UserDTO userDTO) {

        validateMandatoryField(
                userMapper.fromDTO(userDTO).getEmail(),
                userMapper.fromDTO(userDTO).getPhoneNumber()
        );
        validateFirstNameOrLastName(userMapper.fromDTO(userDTO).getFirstName(), "firstName");
        validateFirstNameOrLastName(userMapper.fromDTO(userDTO).getLastName(), "lastName");

        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<ResponseDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    private void validateEmail(String email) {
        //Validate special characters and format
        String regex = "[A-Za-z\\d]+@[A-Za-z\\d]+\\.[A-Za-z]+(.[A-Za-z]+)?";
        if (!email.matches(regex)) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_05, UserDemoErrorCode.CODE_05.getMessage()));
        }

        String[] emailSplinted = email.split("@");
        //Validate Domain
        String validDomain = "@icesi.edu.co";
        String domain = "@" + emailSplinted[1];
        if (!domain.equals(validDomain)) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_06, UserDemoErrorCode.CODE_06.getMessage()));
        }
    }

    private void validatePhone(String phone) {
        String regex = "^\\+57[\\s\\S]*";
        //Validate Prefix
        if (!phone.matches(regex)) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_07, UserDemoErrorCode.CODE_07.getMessage()));
        }

        //Validate spaces and format

        regex = "\\+57\\d{10}";

        if (!phone.matches(regex)) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_08, UserDemoErrorCode.CODE_08.getMessage()));
        }
    }

    private void validateMandatoryField(String email, String phoneNumber) {
        if (email == null && phoneNumber == null) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_09, UserDemoErrorCode.CODE_09.getMessage()));
        }

        if (email != null) {
            validateEmail(email);
        }

        if (phoneNumber != null) {
            validatePhone(phoneNumber);
        }
    }

    private void validateFirstNameOrLastName(String anyName, String option) {
        //Validate max length
        if (anyName != null && anyName.length() > 120) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_10, UserDemoErrorCode.CODE_10.getMessage()));
        }

        //Validate format
        String regex = "[A-Za-z\\s]*";
        if (anyName != null && !anyName.matches(regex)) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_11, UserDemoErrorCode.CODE_11.getMessage()));
        }
    }
}
