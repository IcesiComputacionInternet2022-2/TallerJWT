package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
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
        System.out.println("email"+ validateEmail(userDTO.getEmail()));
        System.out.println("phone"+ phoneValidation(userDTO.getPhoneNumber()));
        System.out.println("name"+ firstNamelength(userDTO.getFirstName()));
        System.out.println("last"+lastNamelength(userDTO.getLastName()));
        if(phoneValidation(userDTO.getPhoneNumber()) && validateEmail(userDTO.getEmail()) && firstNameValidation(userDTO.getFirstName()) && lastNameValidation(userDTO.getLastName()))
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        else throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("Code", "Invalid"));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
//    public boolean validateAtLeast(String phone,String email){
//       boolean atLeast = false;
//        if(phone == null && email!=null){
//            atLeast = true;
//        }
//        if(phone != null && email == null){
//            atLeast = true;
//        }
//        if(phone != null && email != null){
//            atLeast = true;
//        }
//        return atLeast;
//    }
    public boolean validateEmail(String email){
        return(emailDomain(email));
    }

    public boolean emailDomain(String email){
        boolean domain = false;
        String[] newStr =  email.split("@");
        if(newStr[0].matches("^[a-zA-Z0-9]*$") && newStr[1].equals("icesi.edu.co")){
            domain = true;
        }
        return domain;
    }
    public boolean phoneSpaces(String phone){
        boolean spaces = false;
        int contador = 0;
        for (int i = 0; i < phone.length(); i++){
            if (phone.charAt(i) == ' ') {
                contador++;
            }
        }
        if(contador == 0){
            spaces = true;
        }
        return spaces;
    }
    public boolean phoneValidation(String phone) {
        boolean isNumber = false;
        if(phonePrefix(phone) && phoneLength(phone) && phoneSpaces(phone) && phoneFormat(phone)){
            isNumber = true;
        }
        return isNumber;
    }
    public boolean phonePrefix(String phone){
        return (phone.startsWith("+57"));
    }
    public boolean phoneLength(String phone){
        return ( (phone.length() == 13));
    }
    public boolean phoneFormat(String phone){
        return (phone.replace("+","").matches("[0-9]+"));
    }

    public boolean firstNameValidation(String firstName){
        return (firstNameNullEmpty(firstName) && firstNamelength(firstName) && firstNameCharacters(firstName));
    }
    public boolean firstNameNullEmpty(String firstName){
        return (!firstName.isEmpty() && !firstName.equals(" "));
    }
    public boolean firstNamelength(String firstName){
        return(firstName.length() <= 120);
    }
    public boolean firstNameCharacters(String firstName){
        return(firstName.matches("^[a-zA-Z]*$"));
    }
    public boolean lastNameValidation(String lastName){
        return(lastNameNullEmpty(lastName) && lastNameCharacters(lastName) && lastNamelength(lastName));
    }
    public boolean lastNameNullEmpty(String lastName){
        return (!lastName.isEmpty() && !lastName.equals(" "));
    }
    public boolean lastNamelength(String lastName){
        return(lastName.length() <= 120);
    }
    public boolean lastNameCharacters(String lastName){
        return(lastName.matches("^[a-zA-Z]*$"));
    }


}
