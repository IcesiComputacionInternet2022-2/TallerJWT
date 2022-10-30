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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.icesi.edu.users.constants.UserErrorCode.CODE_001;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserCreateDTO getUser(UUID ID) {
        return userMapper.CreateUser(userService.getUser(ID));
    }

    @Override
    public UserCreateDTO createUser(@Valid UserCreateDTO userDTO) {
        if (validateUser(userDTO)){
            return  userMapper.CreateUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }
        throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(CODE_001.toString(), CODE_001.getMessage()));
    }

    public boolean validateUser(UserCreateDTO user){
        if (user.getEmail()!=null){
            if(user.getPhoneNumber()!=null){
                return validateEmail(user.getEmail())
                        &&validateNumber(user.getPhoneNumber())
                        &&validateName(user.getFirstName()+""+user.getLastName());
            }
            return validateEmail(user.getEmail())
                    &&validateName(user.getFirstName()+""+user.getLastName());

        }
        else{
            if(user.getPhoneNumber()!=null){
                return  validateNumber(user.getPhoneNumber())
                        &&validateName(user.getFirstName()+""+user.getLastName());
            }
            else{
                return false;
            }
        }
    }

    private boolean validateEmail(String email){
        return email.matches("\\w+@icesi.edu.co$");
    }
    private boolean validateNumber(String phoneNumber){
        return phoneNumber.matches("^(\\+57)[0-9]{10}");
    }
    private boolean validateName(String fullName){
        return fullName.matches("[aA-zZ ]{0,240}") ;
    }
    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }



}
