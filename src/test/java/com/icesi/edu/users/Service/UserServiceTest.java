package com.icesi.edu.users.Service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private User user;
    private User aux;
    private UUID uuid;

    @BeforeEach
    private void init() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    private void scene1() {
        uuid = UUID.randomUUID();
        String email = "example8041@icesi.edu.co";
        String phoneNumber = "+573116836196";
        String firstName = "Perez";
        String lastName = "Pereza";
        String password = "Rorschach8041";

        user = new User(uuid, email, phoneNumber, firstName, lastName,password);
    }
    private void scene2() {
        uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        String email2 = "example2@icesi.edu.co";
        String phoneNumber2 = "+573456789090";
        String firstName2 = "Pedro";
        String lastName2 = "Picapapas";
        String password = "Rorschach8920";

        aux = new User(uuid2, email2, phoneNumber2, firstName2, lastName2,password);
    }

    @Test
    public void testCreateUser() {
        scene1();
        when(userRepository.save(any())).thenReturn(new User());
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser); //User is not null
        verify(userRepository, times(1)).save(any()); //Save is being called
    }

    @Test
    public void testGetUser() {
        scene1();
        userService.createUser(user);
        User obtainedUser = userService.getUser(uuid);
        verify(userRepository, times(1)).findById(any()); //Save is being called
    }

    @Test
    public void testGetUsers() {
        scene1();
        scene2();
        userService.createUser(user);
        userService.createUser(aux);
        userService.getUsers();
        verify(userRepository, times(3)).findAll();
    }

}