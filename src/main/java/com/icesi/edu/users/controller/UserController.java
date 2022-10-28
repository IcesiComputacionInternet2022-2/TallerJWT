package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.passay.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserCreateDTO userDTO) {
        if (validateEmailPhoneNull(userDTO) && validateEmail(userDTO.getEmail()) && validatePhoneNumber(userDTO.getPhoneNumber()) && validateFirstName(userDTO.getFirstName()) && ValidateLastName(userDTO.getLastName()) && validatePassword(userDTO.getPassword())) {
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        } else {
            return null;
        }
    }

    private boolean validateEmailPhoneNull(UserCreateDTO userDTO) {
        if (userDTO.getPhoneNumber() != null || userDTO.getEmail() != null) {
            return true;
        } else {
            throw new UserDemoException(HttpStatus.NO_CONTENT, new UserDemoError("code", "There must be a Phone Number or Email"));
        }
    }

    private boolean validateFirstName(String firstName) {
        Pattern range = Pattern.compile("[^A-Za-z ]");
        Matcher validName = range.matcher(firstName);
        if (!validName.find()) {
            return true;
        } else {
            throw new RuntimeException("Invalid First Name");
        }
    }

    private boolean ValidateLastName(String lastName) {
        Pattern range = Pattern.compile("[^A-Za-z ]");
        Matcher validName = range.matcher(lastName);
        if (!validName.find()) {
            return true;
        } else {
            throw new RuntimeException("Invalid Last Name");
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 13 && phoneNumber.startsWith("+57") && phoneNumber.length() == 13 && phoneNumber.substring(3).matches("^[0-9]*$")) {
            return true;
        } else {
            throw new RuntimeException("Invalid Phone Number");
        }
    }

    private boolean validateEmail(String email) {
        if (email.split("@")[1].equals("icesi.edu.co") && (email.split("@")[0].matches("^[0-9a-zA-z]*$"))) {
            return true;
        } else {
            throw new RuntimeException("Invalid Email");
        }
    }
    private boolean validatePassword(String password) {
        PasswordValidator passwordValidator = new PasswordValidator(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );
        RuleResult ruleResult = passwordValidator.validate(new PasswordData(password));
        if(ruleResult.isValid()){
            return true;
        }else{
            throw new UserDemoException(HttpStatus.NOT_ACCEPTABLE, new UserDemoError("406", "Invalid Password: Must have at least: 1 Uppercase, 1 Lowercase, 1 Number and a special symbol such as #$%@"));
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}