package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserSensibleDTO;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.icesi.edu.users.constant.UserErrorCode.*;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    public final UserService userService;
    public final UserMapper userMapper;

    public final String EMAIL_PATTERN = "^[a-zA-Z0-9._-]+@icesi\\.edu\\.co$";
    public final String PHONE_PATTERN = "^\\+57[0-9]{10}$";
    public final String NAME_CHAR_PATTERN = "[a-zA-Z\\s]+";
    public final String NAME_SIZE_PATTERN = "^[a-zA-Z\\s]{1,120}$";
    public final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#$%@])$";

    @Override
    public UserSensibleDTO getUser(UUID userId) throws RuntimeException{
        if(userId == null){
            throw new RuntimeException("UserID can't be empty");
        }else{
            return userMapper.fromUserToSensibleDTO(userService.getUser(userId));
        }
    }

    @Override
    public UserSensibleDTO createUser(UserSensibleDTO userDTO) throws RuntimeException{
        validateEmailOrPhone(userDTO);
        validateEmailDomain(userDTO.getEmail());
        validateEmailUsername(userDTO.getEmail());
        validatePhoneNumber(userDTO.getPhoneNumber());
        validateFirstName(userDTO.getFirstName());
        validateLastName(userDTO.getLastName());
        validateFirstNameLength(userDTO.getFirstName());
        validateLastNameLength(userDTO.getLastName());
        return userMapper.fromUserToSensibleDTO(userService.createUser(userMapper.fromSensibleDTOToUser(userDTO)));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUserToDTO).collect(Collectors.toList());
    }

    private void validateEmailOrPhone(UserSensibleDTO userDTO){
        if(userDTO.getEmail() == null || userDTO.getPhoneNumber() == null){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C101, C101.getErrorMessage()));
        }
    }

    private void validateEmailDomain(String email){
        if(email.matches(EMAIL_PATTERN) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C102, C102.getErrorMessage()));
        }
    }

    private void validateEmailUsername(String email){
        if(email.matches(EMAIL_PATTERN) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C103, C103.getErrorMessage()));
        }
    }

    private void validatePhoneNumber(String phone){
        if(phone.matches(PHONE_PATTERN) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C104, C104.getErrorMessage()));
        }
    }

    private void validateFirstName(String firstName){
        if(firstName.matches(NAME_CHAR_PATTERN) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C105, C105.getErrorMessage()));
        }
    }

    private void validateLastName(String lastName){
        if(lastName.matches(NAME_CHAR_PATTERN) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C106, C106.getErrorMessage()));
        }
    }

    private void validateFirstNameLength(String firstName){
        if(firstName.matches(NAME_SIZE_PATTERN) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C107, C107.getErrorMessage()));
        }
    }

    private void validateLastNameLength(String lastName){
        if(lastName.matches(NAME_SIZE_PATTERN) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C108, C108.getErrorMessage()));
        }
    }

    private void validatePassword(String password){
        if(password.matches(PASSWORD_PATTERN) == false){
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(C109, C109.getErrorMessage()));
        }
    }

}
