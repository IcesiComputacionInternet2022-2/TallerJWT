package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
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
        User user = userRepository.findById(userId).orElse(null);
        if(user != null){
           user.setLastTimeSearched(LocalDate.now());
           System.out.println("Fecha: " + user.getLastTimeSearched());
        }
        return user;
    }

    @Override
    public User createUser(User userDTO) {
        System.out.println("Service: " + userDTO.getHashedPassword());
        if(!repeatedPhoneOrEmail(userDTO.getEmail(),userDTO.getPhoneNumber()))
            return userRepository.save(userDTO);
        throw new UserException(HttpStatus.CONFLICT,  new UserError(UserErrorCode.CODE_06, UserErrorCode.CODE_06.getMessage()));
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    public boolean repeatedPhoneOrEmail(String email, String number){
        List<User> allUsers = getUsers();
        boolean duplicatedData = false;

        for(User user : allUsers){
            if (user.getEmail() != null && email != null && email.equals(user.getEmail()) ||
                    user.getPhoneNumber() != null && number != null && number.equals(user.getPhoneNumber())) {
                duplicatedData = true;
                break;
            }
        }

        return duplicatedData;
    }


}
