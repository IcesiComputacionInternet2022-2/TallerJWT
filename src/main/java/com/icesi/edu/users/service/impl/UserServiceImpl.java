package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.exception.UserError;
import com.icesi.edu.users.exception.UserException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Override
    public User getUser(UUID userId){
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User user){
        if(!isEmailOrPhoneNumberDuplicated(user))
            return userRepository.save(user);
        else
            throw new UserException(HttpStatus.BAD_REQUEST, new UserError(UserErrorCode.CODE_05, UserErrorCode.CODE_05.getMessage()));
    }

    private boolean isEmailOrPhoneNumberDuplicated(User user){
        boolean isEmailDuplicated = getUsers().stream().anyMatch(
                u -> u.getEmail().equalsIgnoreCase(user.getEmail()));
        boolean isPhoneNumberDuplicated = getUsers().stream().anyMatch(
                u -> u.getPhoneNumber().equals(user.getPhoneNumber()));
        return isEmailDuplicated || isPhoneNumberDuplicated;
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
