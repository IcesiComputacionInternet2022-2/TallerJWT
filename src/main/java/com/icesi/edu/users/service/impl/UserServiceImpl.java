package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public User createUser(User user) {
        if(!verifyUser(user.getEmail(),user.getPhoneNumber())){
            return userRepository.save(user);
        }
        throw new RuntimeException(String.valueOf(CODE_003));
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private boolean verifyUser(String email,String phoneNumber){
        List<User> users = getUsers();
        for (User x : users){
            if (x.getPhoneNumber().equals(phoneNumber) || x.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
}
