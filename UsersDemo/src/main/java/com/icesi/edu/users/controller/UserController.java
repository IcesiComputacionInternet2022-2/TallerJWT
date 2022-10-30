package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.constant.UserDemoErrorCode;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTORequest;
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
    public UserDTORequest getUser(UUID userId) {
        return userMapper.fromUserToDTORequest(userService.getUser(userId));
    }

    @Override
    public UserCreateDTO createUser(@Valid UserCreateDTO userCreateDTO) throws RuntimeException {
        boolean isValidUser = validateUser(userCreateDTO);
        if(isValidUser){
            return userMapper.fromUserToCreateDTO(userService.createUser(userMapper.fromCreateDTO(userCreateDTO)));
        }else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_UD_01, UserDemoErrorCode.CODE_UD_01.getMessage()));
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    private boolean validateUser(UserCreateDTO userCreateDTO){

        boolean isValidUser = false;
        UserValidations fieldValidation = validateUserNulls(userCreateDTO);
        switch (fieldValidation){
            case NOT_NULLS:
                isValidUser = validateUserEmail(userCreateDTO) && validateUserPhoneNumber(userCreateDTO) && validateUserNames(userCreateDTO);
                break;

            case EMAIL_NULL:
                isValidUser = validateUserPhoneNumber(userCreateDTO) && validateUserNames(userCreateDTO);
                break;

            case PHONE_NULL:
                isValidUser = validateUserEmail(userCreateDTO) & validateUserNames(userCreateDTO);
                break;

            case EMAIL_PHONE_NULL:
                break;
        }

        return isValidUser;
    }

    private UserValidations validateUserNulls(UserCreateDTO userCreateDTO){
        UserValidations fieldValidation = UserValidations.EMAIL_PHONE_NULL;
        if(validateUserEmailNotNull(userCreateDTO.getEmail()) && validateUserPhoneNotNull(userCreateDTO.getPhoneNumber())){
            fieldValidation = UserValidations.NOT_NULLS;
        }

        if(!validateUserEmailNotNull(userCreateDTO.getEmail()) && validateUserPhoneNotNull(userCreateDTO.getPhoneNumber())){
            fieldValidation = UserValidations.EMAIL_NULL;
        }

        if(validateUserEmailNotNull(userCreateDTO.getEmail()) && !validateUserPhoneNotNull(userCreateDTO.getPhoneNumber())){
            fieldValidation = UserValidations.PHONE_NULL;
        }

        return fieldValidation;
    }

    private boolean validateUserPhoneNotNull(String phoneNumber) {
        return phoneNumber != null;
    }

    private boolean validateUserEmailNotNull(String email) {
        return email != null;
    }

    private boolean validateUserNames(UserCreateDTO userCreateDTO) {
        return validateUserNamesSize(userCreateDTO) && validateUserNamesContent(userCreateDTO);
    }

    private boolean validateUserNamesContent(UserCreateDTO userCreateDTO) {
        return userCreateDTO.getFirstName().matches("[a-zA-Z]+") && userCreateDTO.getLastName().matches("[a-zA-Z]+");
    }

    private boolean validateUserNamesSize(UserCreateDTO userCreateDTO) {
        return userCreateDTO.getFirstName().length() <= 120 && userCreateDTO.getLastName().length() <= 120;
    }

    private boolean validateUserPhoneNumber(UserCreateDTO userCreateDTO) {
        return validateUserPhoneNumberExtension(userCreateDTO) && validateUserPhoneNumberContent(userCreateDTO);
    }

    private boolean validateUserPhoneNumberContent(UserCreateDTO userCreateDTO) {
        String phoneNumber = userCreateDTO.getPhoneNumber();
        return phoneNumber.length() == "+57XXXXXXXXXX".length() && phoneNumber.replace("+","").matches("[0-9]+");
    }

    private boolean validateUserPhoneNumberExtension(UserCreateDTO userCreateDTO) {
        return userCreateDTO.getPhoneNumber().startsWith("+57");
    }

    private boolean validateUserEmail(UserCreateDTO userCreateDTO){
        String[] email = userCreateDTO.getEmail().split("@");
        if(email.length == 2){
            return  validateUserEmailDomain(email[1]) && validateUserEmailSpecialChars(email[0]);
        }
        throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_UD_11, UserDemoErrorCode.CODE_UD_11.getMessage()));

    }

    private boolean validateUserEmailSpecialChars(String user) {
        if(user.matches("[a-zA-Z0-9]+")){
            return true;
        }
        throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_UD_02, UserDemoErrorCode.CODE_UD_02.getMessage()));
    }

    private boolean validateUserEmailDomain(String domain) {
        if(domain.equals("icesi.edu.co")){
            return true;
        }
        throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_UD_01, UserDemoErrorCode.CODE_UD_01.getMessage()));

    }

    
}
