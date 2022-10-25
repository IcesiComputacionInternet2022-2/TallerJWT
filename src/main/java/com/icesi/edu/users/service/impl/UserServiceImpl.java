package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        User retUser = userRepository.findById(userId).orElse(null);
        if (retUser != null) {
            String idString = retUser.getId().toString();
            int len = idString.length();
            String idStringCover = "00000000-0000-0000-0000-00000000" + idString.substring(len - 4, len - 1) + idString.charAt(len - 1);
            retUser.setId(UUID.fromString(idStringCover));
            retUser.setModifiedTime(LocalDateTime.now());
        }
        return retUser;
    }

    @Override
    public User createUser(User userDTO) {
        return (uniqueEmail(userDTO.getEmail()) && uniquePhone(userDTO.getPhoneNumber())) ? userRepository.save(userDTO) : null;
    }

    private List<User> getUsersRaw() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    public List<User> getUsers() {
        List<User> users = getUsersRaw();
        for (User u : users) {
            String idString = u.getId().toString();
            int len = idString.length();
            String idStringCover = "00000000-0000-0000-0000-00000000" + idString.substring(len - 4, len - 1) + idString.charAt(len - 1);
            u.setId(UUID.fromString(idStringCover));
        }
        return users;
    }


    /* Validations */

    private boolean uniquePhone(String phone) {
        for (User u : getUsers()) if (u.getPhoneNumber().equals(phone)) return false;
        return true;
    }

    private boolean uniqueEmail(String email) {
        for (User u : getUsers()) if (u.getEmail().equals(email)) return false;
        return true;
    }
}
