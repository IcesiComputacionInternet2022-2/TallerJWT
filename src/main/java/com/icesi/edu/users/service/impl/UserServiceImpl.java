package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.ErrorConstants;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserDemoException(HttpStatus.NOT_FOUND, new UserDemoError("11", ErrorConstants.CODE_UD_11)));
    }

    @Override
    public User createUser(User userDTO) {

        List<User> users = getUsers();

        // Avoid adding duplicated data
        for(int i=0; i<users.size();i++){
            if(users.get(i).getEmail().equals(userDTO.getEmail())||users.get(i).getPhoneNumber().equals(userDTO.getPhoneNumber())){

                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("CODE_UD_12", "Duplicated email or phone number"));
            }
        }
 

        return userRepository.save(userDTO);
    }

    @Override
    public List<User> getUsers() {
        return   StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
