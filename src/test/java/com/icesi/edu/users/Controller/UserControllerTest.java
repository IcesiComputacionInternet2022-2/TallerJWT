package com.icesi.edu.users.controller;

import com.icesi.edu.users.controller.UserController;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserSensibleDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserMapper userMapper;
    private UserService userService;
    private UserSensibleDTO testUser;
    private User userResponse;
    private UUID id1;

    @BeforeEach
    public void init(){
        userService =  mock(UserService.class);
        userMapper = mock(UserMapper.class);

        userController = new UserController(userService, userMapper);

        id1 = UUID.randomUUID();
        testUser = new UserSensibleDTO(id1, "juan@icesi.edu.co", "+573012345678", "Juan Jose", "Calderon", "aA1@");
        userResponse = new User(id1, "juan@icesi.edu.co", "+573012345678", "Juan Jose", "Calderon", "aA1@");

        when(userService.createUser(any())).thenReturn(userResponse);
    }

    private boolean throwsException(UserSensibleDTO argUser){
        try{
            UserSensibleDTO userDto = userController.createUser(argUser);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    @Test
    public void testCreateValidUser(){
        assertFalse(throwsException(testUser));
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testCreateUserWithoutEmailNorPhone(){
        testUser.setEmail(null);
        testUser.setPhoneNumber(null);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithEmailDomainWrong(){
        testUser.setEmail("juan@gmail.edu.co");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithEmailUsernameWrong(){
        testUser.setEmail("!:{}*&+@icesi.edu.co");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithCountryIndicatorWrong1(){
        testUser.setPhoneNumber("+123012345678");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithCountryIndicatorWrong2(){
        testUser.setPhoneNumber("573012345678");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidAmountOfNumberSizeShorter(){
        testUser.setPhoneNumber("+573012345");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidAmountOfNumberSizeLonger(){
        testUser.setPhoneNumber("+5730123456789");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithEmptyFirstName(){
        testUser.setFirstName(null);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithEmptyLastName(){
        testUser.setLastName(null);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithLongerFirstName(){
        String longName = "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "a";
        testUser.setFirstName(longName);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithLongerLastName(){
        String longName = "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "abcdefghij" +
                "a";
        testUser.setLastName(longName);
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithFirstNameWithSpecialCharacters(){
        testUser.setFirstName("Juan#34");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithLastNameWithSpecialCharacters(){
        testUser.setLastName("C4lder0n^_^");
        assertTrue(throwsException(testUser));
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testGetUserCorrectly(){
        when(userController.getUser(id1)).thenReturn(testUser);
        assertEquals(testUser, userController.getUser(id1));
        verify(userService, atLeastOnce()).getUser(any());
    }

    @Test
    public void testGetUserNull(){
        when(userService.getUser(null)).thenReturn(null);
        boolean exception = false;
        try{
            userController.getUser(null);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            exception = true;
        }
        assertTrue(exception);
        verify(userService, never()).getUser(any());
    }

}
