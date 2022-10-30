package com.icesi.edu.users.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testGetUser(){

        UUID id = UUID.randomUUID();
        User user = new User(id, "juca@gmail.com", "+573186215574", "Juca", "Ota");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userService.getUser(id);
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void testGetNotSavedUser(){

        UUID id = UUID.randomUUID();
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertNull(userService.getUser(id));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void testCreateUser(){

        UUID id = UUID.randomUUID();
        String email = "juca@icesi.edu.co";
        String phoneNumber = "+573186215574";
        String firstName = "Juca";
        String lastName = "Ota";
        User user = new User(id, email, phoneNumber, firstName, lastName);

        when(userRepository.save(any())).thenReturn(new User(id, email, phoneNumber, firstName, lastName));
        when(userRepository.findAll()).thenReturn(new ArrayList<User>());
        userService.createUser(user);
        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUsers(){
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);
        userService.getUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testExistUserWithSamePhoneNumber(){

        String phoneNumber = "+573186215574";
        User newUser = new User(UUID.randomUUID(), "juca@icesi.edu.co", phoneNumber, "Juca", "Ota");
        User existingUser = new User(UUID.randomUUID(), "jota@icesi.edu.co", phoneNumber, "Juca", "Ota");

        List<User> users = new ArrayList<>();
        users.add(existingUser);
        when(userRepository.findAll()).thenReturn(users);

        try {
            userService.createUser(newUser);
            fail();
        }catch (RuntimeException exception){
            verify(userRepository, times(0)).save(any());
            verify(userRepository, times(1)).findAll();
        }
    }

    @Test
    public void testExistUserWithSameEmail(){
        String email = "juca@icesi.edu.co";
        User newUser = new User(UUID.randomUUID(), email, "+573186215574", "Juca", "Ota");
        User existingUser = new User(UUID.randomUUID(), email, "+573109419971", "Juca", "Ota");

        List<User> users = new ArrayList<>();
        users.add(existingUser);
        when(userRepository.findAll()).thenReturn(users);

        try {
            userService.createUser(newUser);
            fail();
        }catch (RuntimeException exception){
            verify(userRepository, times(0)).save(any());
            verify(userRepository, times(1)).findAll();
        }
    }

}
