package com.icesi.edu.users.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserRepository userRepository;

    private UserService userService;

    private User testUser;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }


    private void createTestUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User(null,"example@icesi.edu.co","+573161234567","Pepito","Perez"));
        users.add(new User(null,"example2@icesi.edu.co","+573171234567","Juan","Martinez"));

        when(userRepository.findAll()).thenReturn(users);
    }


    @Test
    public void testVerifyRepeatedEmailDifferentCase(){
        createTestUsers();

        testUser = new User(null,"ExamplE@icesi.edu.co","+573201234567","Daniel","Hidalgo");

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userService.createUser(testUser);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Repeated email or phone number");

    }

    @Test
    public void testVerifyRepeatedEmailSameCase(){
        createTestUsers();

        testUser = new User(null,"example@icesi.edu.co","+573201234567","Daniel","Hidalgo");

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userService.createUser(testUser);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Repeated email or phone number");

    }

    @Test
    public void testVerifyRepeatedEmailCorrect(){
        createTestUsers();

        testUser = new User(null,"example3@icesi.edu.co","+573201234567","Daniel","Hidalgo");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testUser);

        User user = userService.createUser(testUser);
        assertEquals(testUser, user);

    }

    @Test
    public void testVerifyRepeatedPhoneNumberRepeated(){
        createTestUsers();

        testUser = new User(null,"example34@icesi.edu.co","+573171234567","Daniel","Hidalgo");

        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> {
                    userService.createUser(testUser);
                }, "RuntimeException expected");

        assertEquals(thrown.getMessage(),"Repeated email or phone number");

    }

    @Test
    public void testVerifyRepeatedPhoneNumberNotRepeated(){
        createTestUsers();

        testUser = new User(null,"example34@icesi.edu.co","+573201234567","Daniel","Hidalgo");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testUser);

        User user = userService.createUser(testUser);
        assertEquals(testUser, user);
    }

    @Test
    public void testCreateUser(){


        testUser = new User(null,"example@icesi.edu.co","+573161234567","Pepito","Perez");

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(testUser);

        User user = userService.createUser(testUser);
        assertEquals(testUser,user);
    }

    @Test
    public void testGetUser(){
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        User user = userService.getUser(null);
        assertNull(user);
    }


    @Test
    public void testGetUsers(){

        when(userRepository.findAll()).thenReturn(new ArrayList<User>());

        List<User> users = userService.getUsers();
        assertTrue(users.isEmpty());
    }











}
