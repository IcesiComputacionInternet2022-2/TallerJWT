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
        validateRepeatedEmailOrPhoneNumber(userDTO.getEmail(), userDTO.getPhoneNumber());
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


    private void validateRepeatedEmailOrPhoneNumber(String email, String phoneNumber) {

        List<User> listUsers = StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());

        listUsers.forEach(user-> {
            if(user.getEmail()!=null&&user.getEmail().equals(email)){
                throw new RuntimeException("The email is already in use");
            }
            if(user.getPhoneNumber()!=null&&user.getPhoneNumber().equals(phoneNumber)){
                throw new RuntimeException("The phone number is already in use");
            }
        });
    }
}
