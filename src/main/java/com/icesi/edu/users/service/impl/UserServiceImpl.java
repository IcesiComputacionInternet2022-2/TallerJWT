package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
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

import static com.icesi.edu.users.error.constants.ErrorCode.CODE_02;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User user) {

        if(verifyRepeatedEmail(user) && verifyRepeatedPhoneNumber(user)){
            return userRepository.save(user);
        }
        else{
            throw new UserDemoException(HttpStatus.CONFLICT,new UserDemoError(CODE_02,CODE_02.getMessage()));
        }
    }

    private boolean verifyRepeatedEmail(User user){

        if(user.getEmail() == null) return true;

        List<User> users = getUsers();

        for(User registeredUser: users){

            if(user.getEmail().equalsIgnoreCase(registeredUser.getEmail().toLowerCase())){
                return false;
            }
        }

        return true;
    }

    private boolean verifyRepeatedPhoneNumber(User user){

        if(user.getPhoneNumber() == null) return true;

        List<User> users = getUsers();

        for(User registeredUser: users){

            if(user.getPhoneNumber().equals(registeredUser.getPhoneNumber())){
                return false;
            }
        }

        return true;
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
