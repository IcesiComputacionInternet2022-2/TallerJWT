package com.icesi.edu.users.services;

import com.icesi.edu.users.dto.UserDTO;
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



    private UserRepository userRepository;
    private UserService userService;
    private User user;
    private User anotherUser;
    private List<User> usersList;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    public void setupScenary1() {

        String email = "nicolasp@icesi.edu.co";
        String phoneNumber = "+573166017116";
        String name = "Nicolas";
        String lastName = "Penagos";

        user = new User(UUID.randomUUID(), email, phoneNumber, name, lastName, "");


    }

    public void setupScenary2() {

        String email = "nicolasp@icesi.edu.co";
        String phoneNumber = "+573166017116";
        String name = "Nicolas";
        String lastName = "Penagos";

        user = new User(UUID.randomUUID(), email, phoneNumber, name, lastName, "");

        String anotherEmail = "juan@icesi.edu.co";
        String anotherPhoneNumber = "+573164807322";
        String anotherName = "Juan";
        String anotherLastName = "Reyes";

        anotherUser = new User(UUID.randomUUID(), anotherEmail, anotherPhoneNumber, anotherName, anotherLastName, "");

        usersList = new ArrayList<>();

        usersList.add(user);
        usersList.add(anotherUser);


    }

    @Test
    public void createUserOk() {

        setupScenary1();
        when(userRepository.save(any())).thenReturn(user);
        User result = userService.createUser(anotherUser);
        //Agregar fail

        userService.createUser(anotherUser);
        verify(userRepository,times(2)).save(any());
    }

    @Test
    public void getUsersOk(){
        setupScenary2();
        when(userRepository.findAll()).thenReturn(usersList);
        userService.getUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUserTest(){

        setupScenary1();
        when(userRepository.findAllById(any())).thenReturn(any());
        try {
            userService.getUser(user.getUserId());
        }catch (RuntimeException e){

        }

        verify(userRepository, times(1)).findById(any());

    }

    @Test
    public void testCreateUserWithRepeatedEmail(){

        setupScenary2();

        String email ="nicolasp@icesi.edu.co";;
        String phoneNumber = "+573166017117";
        String name = "Jose";
        String lastName = "Mora";

        try {
            userService.createUser(user);
            verify(userRepository, times(1)).save(any());
            when(userService.getUsers()).thenReturn(usersList);
            userService.createUser(new User(UUID.randomUUID(), email, phoneNumber,name, lastName, ""));
            fail("RuntimeException expected");
        }catch (RuntimeException e){
            assertEquals(2, userService.getUsers().size());
        }


    }



}
