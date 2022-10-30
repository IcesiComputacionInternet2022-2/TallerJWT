package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    public User createUser(User user) {
        validateUser(user.getEmail(), user.getPhoneNumber());
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    private void validateUser(String email, String phoneNumber) {
        getUsers().forEach(user -> {
            if ((user.getEmail() != null && user.getEmail().equals(email)) || (user.getPhoneNumber() != null && user.getPhoneNumber().equals(phoneNumber)))
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("05", UserErrorCode.CODE_UD_05));
        });
    }
}