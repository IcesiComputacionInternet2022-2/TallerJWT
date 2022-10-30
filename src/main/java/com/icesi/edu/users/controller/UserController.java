package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.dto.UserCreateDTO;
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

    public final static String DOMAIN = "@icesi.edu.co";

    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserCreateDTO getUser(UUID userId) {
        return userMapper.userCreateDTOfromUser(userService.getUser(userId));
    }

    @Override
    public UserCreateDTO createUser(UserCreateDTO userCreateDTO) {
    	isValidUser(userCreateDTO);
    	isValidEmailDomain(userCreateDTO);
    	isValidEmail(userCreateDTO);
    	isValidPrefixPhoneNumber(userCreateDTO);
    	isValidPhoneNumberLength(userCreateDTO);
    	isValidPhoneNumber(userCreateDTO);
    	isValidNameLengthSum(userCreateDTO);
    	isValidName(userCreateDTO);
    	return userMapper.userCreateDTOfromUser(userService.createUser(userMapper.fromDTO(userCreateDTO)));
    }

    private void isValidUser(UserCreateDTO userCreateDTO){
    	if(userCreateDTO == null || (userCreateDTO.getEmail() == null && userCreateDTO.getPhoneNumber() == null) || userCreateDTO.getFirstName() == null || userCreateDTO.getLastName() == null)
    		throw new RuntimeException();
    }

    private void isValidEmailDomain(UserCreateDTO userCreateDTO){
    	if(userCreateDTO.getEmail() != null && !userCreateDTO.getEmail().endsWith(DOMAIN))
    		throw new RuntimeException();
    }

    private void isValidEmail(UserCreateDTO userCreateDTO){
    	if(userCreateDTO.getEmail() != null && !userCreateDTO.getEmail().substring(0, userCreateDTO.getEmail().length() - DOMAIN.length()).matches("^[a-zA-Z0-9]*$"))
    		throw new RuntimeException();
    }

    private void isValidPrefixPhoneNumber(UserCreateDTO userCreateDTO){
    	if(userCreateDTO.getPhoneNumber() != null && !userCreateDTO.getPhoneNumber().startsWith("+57"))
    		throw new RuntimeException();
    }
    
    private void isValidPhoneNumberLength(UserCreateDTO userCreateDTO){
    	if(userCreateDTO.getPhoneNumber() != null && !(userCreateDTO.getPhoneNumber().substring(3).length() == 10))
    		throw new RuntimeException();
    }
    
    private void isValidPhoneNumber(UserCreateDTO userCreateDTO){
    	if(userCreateDTO.getPhoneNumber() != null && !userCreateDTO.getPhoneNumber().substring(3).matches("^[0-9]*$"))
    		throw new RuntimeException();
    }

    private void isValidNameLengthSum(UserCreateDTO userCreateDTO){
        if(userCreateDTO.getFirstName().length() + userCreateDTO.getLastName().length() > 120)
        	throw new RuntimeException();
    }

    private void isValidName(UserCreateDTO userCreateDTO){
    	if(!userCreateDTO.getFirstName().concat(userCreateDTO.getLastName()).replaceAll(" ", "").matches("^[a-zA-Z]*$"))
    		throw new RuntimeException();
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }
    
}
