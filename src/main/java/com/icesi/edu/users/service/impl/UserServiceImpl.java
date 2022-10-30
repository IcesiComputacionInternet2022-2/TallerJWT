package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.validation.CustomAnnotations;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.icesi.edu.users.constants.UserErrorCode.CODE_002;
import static com.icesi.edu.users.constants.UserErrorCode.CODE_003;

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
        if(!isRepeated(userDTO.getEmail(),userDTO.getPhoneNumber())){
            return userRepository.save(userDTO);
        }
        throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError(CODE_003.toString(), CODE_003.getMessage()));
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private boolean isRepeated(String email,String number){
        List<User> users = getUsers();
        for (User x : users){
            if (x.getPhoneNumber().equals(number) || x.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
}
