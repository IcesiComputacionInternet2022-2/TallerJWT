package com.icesi.edu.users.service;

import com.icesi.edu.users.controller.UserController;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    private User user;

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        userService = mock((UserService.class));
        List<User> users = new ArrayList<>();
        users.add(new User(null, "mock1@icesi.edu.co", "+573153823657", "Mock", "Mockery", null));
        users.add(new User(null, "mock2@icesi.edu.co", "+573158153968", "Fool", "Tomfoolery", null));
        when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void testGetUser() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        user = userService.getUser(null);
        assertNull(user);
    }

}
