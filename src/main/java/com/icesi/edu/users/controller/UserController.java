package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.validation.CustomAnnotations.*;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {


    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    @IsUser
    public UserCreateDTO getUser(@Param("userId") UUID userId) {
        return userMapper.fromUserWithPassword(userService.getUser(userId));
    }

    @Override
    public UserCreateDTO createUser(@Valid UserCreateDTO userDTO) {
        if (validUser(userDTO.getEmail(),userDTO.getPhoneNumber(),userDTO.getFirstName(),userDTO.getLastName())){
            return  userMapper.fromUserWithPassword(userService.createUser(userMapper.fromDTO(userDTO)));
        }

        throw new RuntimeException("Not a valid user");
    }

    public boolean validUser(String email,String phoneNumber,String name,String lastName){
        switch (validateNotNullEmailOrNumber(email,phoneNumber)){
            case 0: //Both are not null
                return validateEmail(email) && validateNumber(phoneNumber) && validateNameAndLastname(name,lastName);
            case 1: //Email: not null and number: null
                return validateEmail(email) && validateNameAndLastname(name,lastName);
            case 2: //Email: null and number: not null
                return validateNumber(phoneNumber) && validateNameAndLastname(name,lastName);
            default: //both null
                return false;
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
    private boolean validateEmail(String email){
        return email.matches("\\w+@icesi.edu.co$"); //Domain and no special characters
    }
    private boolean validateNumber(String phoneNumber){
        return phoneNumber.matches("^(\\+57)[0-9]{10}"); //+57 and 10 numbers
    }
    private boolean validateNameAndLastname(String name,String lastname){
        return name.matches("[aA-zZ ]{0,120}") && lastname.matches("[aA-zZ ]{0,120}"); //More than 0 lesser than 120
    }

    private int validateNotNullEmailOrNumber(String email,String number){
       if(email != null){
           if(number != null)
               return 0; //Both are not null
           else
               return 1; //Email: not null and number: null
       }
       if(number!=null)
           return 2; //Email: null and number: not null
       else
           return 3; //both null

    }
}
