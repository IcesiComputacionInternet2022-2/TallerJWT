package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exceptions.UserDemoError;
import com.icesi.edu.users.error.exceptions.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public UserDTO createUser(UserDTO userDTO) throws UserDemoException {
        String email = userDTO.getEmail(), phone = userDTO.getPhoneNumber();
        if (!(validDomain(email) && validEmail(email) && validCountryCode(phone) && validCPhone(phone) && emailOrPhone(phone, email) &&
        nameLength(userDTO.getFirstName(), userDTO.getLastName()) && validNameFormat(userDTO.getFirstName(), userDTO.getLastName())))
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("code", "message"));
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }


    /* Validations */

    private boolean validDomain(String email) {
        String domain = "^.*(@icesi.edu.co)$";
        return email != null && email.toLowerCase().matches(domain);
    }

    private boolean validEmail(String email) {
        if (email != null) {
            int atCount = email.length() - email.replaceAll("@", "").length();
            int dotCount = email.length() - email.replaceAll("\\.", "").length();
            String pattern = "[^a-zA-z0-9\\-_]";

            return !email.matches(pattern) && atCount == 1 && dotCount == 2;
        }
        return false;
    }

    private boolean validCountryCode(String phone) {
        return phone != null && phone.substring(0, 3).equals("+57");
    }

    private boolean validCPhone(String phone) {
        return phone != null && phone.length() == 13 && !phone.matches("[^0-9+]");
    }

    private boolean emailOrPhone(String phone, String email) {
        return (email != null && !email.isBlank()) || (phone != null && !phone.isBlank());
    }

    private boolean nameLength(String name, String lastName) {
        int nameLen = name.length();
        int lastLen = lastName.length();
        int CAP = 120;
        return nameLen <= CAP && lastLen <= CAP;
    }

    private boolean validNameFormat(String name, String lastName) {
        String pattern = "[a-zA-Z]+";
        return name.matches(pattern) && lastName.matches(pattern);
    }
}
