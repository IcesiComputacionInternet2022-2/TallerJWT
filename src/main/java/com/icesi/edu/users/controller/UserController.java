package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import javax.validation.Valid;
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

    public UserDTO createUser(UserDTO userDTO) {
        if (validateEmailOrPhoneNumberNotNull(userDTO) && validateFirstName(userDTO) &&
                validateLastName(userDTO)) {
            if (validateUserPhoneNumber(userDTO) && validateUserEmail(userDTO))
                return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
            else
                throw new RuntimeException();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public UserDTO createUser(@Valid UserCreateDTO userDTO) {
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean validateUserEmail(UserDTO userDTO){
        String email = userDTO.getEmail();
        return email != null && email.toLowerCase().endsWith(DOMAIN) &&
                email.substring(0, email.length()-13).matches("[a-zA-Z0-9]+");
    }

    private boolean validateUserPhoneNumber(UserDTO userDTO){
        String phoneNumber = userDTO.getPhoneNumber();
        return phoneNumber != null && phoneNumber.length() == 13 &&
                phoneNumber.startsWith(PREFIX) && phoneNumber.substring(1).matches("[0-9]+");
    }

    private boolean validateEmailOrPhoneNumberNotNull(UserDTO userDTO){
        return userDTO.getEmail() != null || userDTO.getPhoneNumber() != null;
    }

    private boolean validateFirstName(UserDTO userDTO){
        String firstName = userDTO.getFirstName();
        return firstName != null && firstName.length() <= 120 && firstName.matches("[a-zA-Z]+");
    }

    private boolean validateLastName(UserDTO userDTO){
        String lastName = userDTO.getLastName();
        return lastName != null && lastName.length() <= 120 && lastName.matches("[a-zA-Z]+");
    }
}
