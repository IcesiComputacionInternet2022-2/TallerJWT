package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.UserDemoErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User userDTO) {
        validateRepeatedEmailOrPhoneNumber(Optional.ofNullable(userDTO.getEmail()), Optional.ofNullable(userDTO.getPhoneNumber()));
        return userRepository.save(userDTO);
    }

    @Override
    public List<User> getUsers() {
        List<User> listUsers = StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
        return listUsers.stream().peek(user ->{
            String ID = user.getId().toString();
            String newID = ID.substring(ID.length()-4);
            user.setId(UUID.fromString("00000000-0000-0000-0000-00000000"+newID));
        }).collect(Collectors.toList());
    }


    private void validateRepeatedEmailOrPhoneNumber(Optional<String> email, Optional<String> phoneNumber) {

        List<User> listUsers = StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());

        listUsers.forEach(user-> {
            email.ifPresent(e -> {
                if (e.equals(user.getEmail())) {
                    throw new UserDemoException(HttpStatus.BAD_REQUEST,new UserDemoError(UserDemoErrorCode.CODE_12, UserDemoErrorCode.CODE_12.getMessage()));
                }
            });
            phoneNumber.ifPresent(p -> {
                if (p.equals(user.getPhoneNumber())) {
                    throw new UserDemoException(HttpStatus.BAD_REQUEST,new UserDemoError(UserDemoErrorCode.CODE_13, UserDemoErrorCode.CODE_13.getMessage()));
                }
            });
        });
    }
}
