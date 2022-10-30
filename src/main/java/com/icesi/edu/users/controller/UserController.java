package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
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
    public UserDTO getUser(UUID userId) {
        return userMapper.fromUser(userService.getUser(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (validateUser(userDTO)) return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        System.err.println("Something is going bad");
        return null;
    }


    private boolean validateUser(UserDTO user){
        return validateEmail(user.getEmail()) && validatePhone(user.getPhoneNumber()) && validateName(user.getFirstName()) && validateName(user.getLastName());
    }
    private boolean validateEmail(String email){
        String patternEmail = "\\w+@icesi.edu.co$";
        return email.matches(patternEmail);
    }

    private boolean validatePhone(String phone){
        String patternPhone = "^(\\+57)[0-9]{10}";
        return phone.matches(patternPhone);
    }

    private boolean validateName(String name){
        String patternNames = "[aA-zZ ]{0,120}";
        return name.matches(patternNames);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}
