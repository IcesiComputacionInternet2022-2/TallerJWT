package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.config.InitialDataConfig;
import com.icesi.edu.users.constant.ErrorConstants;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTOConsult;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.security.SecurityContextHolder;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Import({InitialDataConfig.class})
public class UserController implements UserAPI {

    private final static String DOMAIN = "@icesi.edu.co";
    private final static String PREFIX = "+57";
    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTOConsult getUser(UUID userId) {
        UserDTOConsult userDTOConsult = userMapper.fromUserToUserDTOConsult(userService.getUser(userId));
        userDTOConsult.setLastCall(LocalDate.now());

        return userDTOConsult;
    }


    @Override
    public UserDTO createUser(@Valid UserDTO userDTO) {
        verifyNulls(userDTO);
        verifyEmail(userDTO);
        verifyPhone(userDTO);
        verifyName(userDTO);

        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    private void verifyNulls(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String phone = userDTO.getPhoneNumber();

        if (email == null || phone == null) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_03.getCode(), ErrorConstants.CODE_03.getMessage()));
        }
    }


    //original
    /*private UserDTO verifyNulls(UserDTO userDTO){
        String email = userDTO.getEmail();
        String phone = userDTO.getPhoneNumber();

        if(email != null && phone != null){
            return verifyAllInputs(userDTO, email, phone);
        }else{
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("Demo","Email and Number are null"));
        }
    }*/

    /*private UserDTO verifyInputsWithoutEmail(UserDTO userDTO, String phone) {
        String fName = userDTO.getFirstName();
        String lName = userDTO.getLastName();

        if(verifyPhone(phone) && verifyName(fName, lName)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            throw new RuntimeException();
        }
    }*/

    /*private UserDTO verifyInputsWithoutPhone(UserDTO userDTO, String email) {
        String fName = userDTO.getFirstName();
        String lName = userDTO.getLastName();

        if(verifyEmail(email) && verifyName(fName, lName)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            throw new RuntimeException();
        }
    }*/

    /*private UserDTO verifyAllInputs(UserDTO userDTO, String email, String phone) {
        String fName = userDTO.getFirstName();
        String lName = userDTO.getLastName();

        if(verifyEmail(email) && verifyPhone(phone) && verifyName(fName, lName)){
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }else{
            throw new RuntimeException();
        }
    }*/

    private void verifyName(UserDTO userDTO) {
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();

        verifyNumCharacters(firstName,lastName);
        verifySpecialCharacters(firstName,lastName);
    }

    private void verifySpecialCharacters(String firstName, String lastName) {
        if(!(firstName.matches("[a-zA-Z]+") && lastName.matches("[a-zA-Z]+"))){
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_10.getCode(), ErrorConstants.CODE_10.getMessage()));
        }
    }

    private void verifyNumCharacters(String firstName, String lastName) {
        if(!(firstName.length() <= 60 && lastName.length() <= 60)){
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_09.getCode(), ErrorConstants.CODE_09.getMessage()));
        }
    }

    private void verifyPhone(UserDTO userDTO) {
        String phone = userDTO.getPhoneNumber();
        validatePrefix(phone);
        validateSpaces(phone);
        validNumber(phone);
    }

    private void validNumber(String phone) {
        String substring = phone.substring(PREFIX.length());
        if(substring.length() != 10){
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_08.getCode(), ErrorConstants.CODE_08.getMessage()));
        }
    }

    private void validateSpaces(String phone) {
        String substring = phone.substring(PREFIX.length());
        if(!substring.matches("[0-9]+")){
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_07.getCode(), ErrorConstants.CODE_07.getMessage()));
        }
    }

    private void validatePrefix(String phone) {
        if(!phone.startsWith(PREFIX)){
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_06.getCode(), ErrorConstants.CODE_06.getMessage()));
        }
    }

    private void verifyEmail(UserDTO userDTO) {
        String email = userDTO.getEmail();
        int idx = email.length() - DOMAIN.length();
        verifyDomain(idx, email);
        validateMail(idx, email);
    }

    public void validateMail(int  endIdx, String email){
        String newEmail = email.substring(0, endIdx);
        if(!newEmail.matches("[a-zA-Z]+")){
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_05.getCode(), ErrorConstants.CODE_05.getMessage()));
        }
    }

    private void verifyDomain(int startIdx,String email){
        String substring = email.substring(startIdx);
        if(!substring.equals(DOMAIN)){
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(ErrorConstants.CODE_04.getCode(), ErrorConstants.CODE_04.getMessage()));
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
}