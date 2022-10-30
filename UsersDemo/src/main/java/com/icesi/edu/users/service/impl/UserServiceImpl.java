package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.ErrorConstants;
import com.icesi.edu.users.constant.UserDemoErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.security.SecurityContextHolder;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Override
    public User getUser(UUID userId) {
        validateUserRequestsOwnInfo(userId);
        return userRepository.findById(userId).orElse(null);
    }

    private void validateUserRequestsOwnInfo(UUID userId) {
        if(!userId.equals(SecurityContextHolder.getContext().getUserId())){
            throw new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserDemoErrorCode.CODE_AUTH_02, UserDemoErrorCode.CODE_AUTH_02.getMessage()));
        }
    }

    @Override
    public User createUser(User userDTO) {
        if(!existRepeatedPhoneNumberOrEmail(userDTO)){
            return userRepository.save(userDTO);
        }
        throw new RuntimeException();
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private boolean existRepeatedPhoneNumberOrEmail(User newUser) {
        boolean repeatedValues = false;
        List<User> users = getUsers();
        for (User user: users) {

            if(validateNotNullEmail(newUser) && newUser.getEmail().equalsIgnoreCase(user.getEmail())){
                repeatedValues = true;
                break;
            }

            if(validateNotNullPhoneNumber(newUser) && newUser.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber())){
                repeatedValues = true;
                break;
            }
        }

        return repeatedValues;
    }

    private boolean validateNotNullEmail(User user){
        if(user.getEmail() != null){
            return true;
        }

        throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_UD_11, UserDemoErrorCode.CODE_UD_11.getMessage()));
    }

    private boolean validateNotNullPhoneNumber(User user){
        if(user.getPhoneNumber() != null){
            return true;
        }

        throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(UserDemoErrorCode.CODE_UD_06, UserDemoErrorCode.CODE_UD_06.getMessage()));
    }

}
