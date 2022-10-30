package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
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
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User createUser(User userDTO) {

        if (availableData(userDTO))return userRepository.save(userDTO);
        return null;
    }

    private boolean availableData(User usr){
        List<User> users = getUsers();
        for (User x:users){
            if (x.getPhoneNumber().equals(usr.getPhoneNumber()) || x.getEmail().equals(usr.getEmail())) return false;
        }

        return true;
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}
