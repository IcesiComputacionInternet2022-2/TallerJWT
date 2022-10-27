package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.ResponseDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
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
    public ResponseDTO getUser(UUID userId) {
        return userMapper.toResponse(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        validateMandatoryField(
                userMapper.fromDTO(userDTO).getEmail(),
                userMapper.fromDTO(userDTO).getPhoneNumber()
        );
        validateFirstNameOrLastName(userMapper.fromDTO(userDTO).getFirstName(), "firstName");
        validateFirstNameOrLastName(userMapper.fromDTO(userDTO).getLastName(), "lastName");

        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private void validateEmail(String email) {
        //Validate special characters and format
        String regex = "[A-Za-z\\d]+@[A-Za-z\\d]+\\.[A-Za-z]+(.[A-Za-z]+)?";
        if (!email.matches(regex)) {
            throw new RuntimeException("The email is invalid");
        }

        String[] emailSplinted = email.split("@");
        //Validate Domain
        String validDomain = "@icesi.edu.co";
        String domain = "@" + emailSplinted[1];
        if (!domain.equals(validDomain)) {
            throw new RuntimeException("The domain is wrong");
        }
    }

    private void validatePhone(String phone) {
        String regex = "^\\+57[\\s\\S]*";
        //Validate Prefix
        if (!phone.matches(regex)) {
            throw new RuntimeException("The phone number must have the colombian prefix");
        }

        //Validate spaces and format

        regex = "\\+57\\d{10}";

        if (!phone.matches(regex)) {
            throw new RuntimeException("The phone number is not valid");
        }
    }

    private void validateMandatoryField(String email, String phoneNumber) {
        if (email == null && phoneNumber == null) {
            throw new RuntimeException("Either email or phone number must be present");
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
        if (anyName!=null&&anyName.length() > 120) {
            throw new RuntimeException("The " + option + " should not have more than 120 characters");
        }

        //Validate format
        String regex = "[A-Za-z\\s]*";
        if (anyName!=null&&!anyName.matches(regex)) {
            throw new RuntimeException("The " + option + " should not have special characters or numbers");
        }
    }
}
