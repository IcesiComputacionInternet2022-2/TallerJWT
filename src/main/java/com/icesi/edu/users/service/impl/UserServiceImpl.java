package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.error.UserErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.security.SecurityContextHolder;
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
    public User getUser(UUID userId) {
        var userAuth= SecurityContextHolder.getContext().getUserId();

        if(userAuth!=null){
            System.out.println(userAuth+" - "+userId);

            if(userAuth.equals(userId)){
                return userRepository.findById(userId).orElse(null);
    }else{
        throw new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserErrorCode.CODE_01,"You cannot see third-party information."));
    }
}else{
        throw new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserErrorCode.CODE_01,"Authentication error"));
        }
    }

    @Override
    public User createUser(User user) {
        verifyEmailRepeat(user.getEmail())  ;
        verifyPhoneNumberRepeat(user.getPhoneNumber());
            return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        List<User> userList=StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());

        return userList;
    }

    public void verifyEmailRepeat(String email){
        for (User i:getUsers()) {
            if (i.getEmail().equals(email)){
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - Email repeated in the database"));
            }
        }
    }

    public void verifyPhoneNumberRepeat(String phoneNumber){
        for (User i:getUsers()) {
            if (i.getPhoneNumber().equals(phoneNumber)){
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("1234","Throw UserDemoException - Phone repeated in the database"));

            }
        }
    }


}
