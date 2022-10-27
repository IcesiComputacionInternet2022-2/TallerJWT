package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public UserDTO createUser( @Valid UserDTO userDTO) {
        String email = userDTO.getEmail();
        String phone = userDTO.getPhoneNumber();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        System.out.println(userDTO.getUnhashedPassword());

        if(!(validateDomain(email) && !hasSpecialCharacters(email) && colombianNumber(phone) && !numberContainsWhiteSpaces(phone) && validSizeNumber(phone)
        && hasAtLeastOneContactWay(email, phone) && namesSizeValidation(firstName, lastName) && !hasSpecialCharactersOnNames(firstName, lastName))){
            throw new RuntimeException("Write valid data and try again. c:");
        }else{
            return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    public boolean validateDomain(String email){
        return email.toUpperCase().matches(".*(@ICESI\\.EDU\\.CO)$|.*(@icesi\\.edu\\.co)$");
    }

    public boolean hasSpecialCharacters(String email){
        String nameEmail = "";

        if(email.split("@").length == 2){
            nameEmail = email.split("@")[0];

        }else{
            return true;
        }
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\-]");
        Matcher matcher = pattern.matcher(nameEmail);
        return matcher.find();
    }

    public boolean colombianNumber(String number){
        return number.substring(0, 3).equalsIgnoreCase("+57");
    }

    public boolean numberContainsWhiteSpaces(String number){
        return number.contains(" ");
    }

    public boolean validSizeNumber(String number){
        if(number != null){
            String actualNumber = number.substring(3);

            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(actualNumber);

            return !matcher.find() && actualNumber.length() == 10;
        }else{
            return true;
        }
    }

    public boolean hasAtLeastOneContactWay(String email, String number){
        return ((email != null) && !email.isBlank()) || ((number != null) && !number.isBlank() );
    }

    public boolean namesSizeValidation(String firstName, String lastName){
        return (firstName.length() <= 120 && lastName.length() <= 120);
    }

    public boolean hasSpecialCharactersOnNames(String firstName, String lastName){

        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        Matcher matcher1 = pattern.matcher(firstName);
        Matcher matcher2 = pattern.matcher(lastName);

        return (matcher1.find() && matcher2.find());
    }
}
