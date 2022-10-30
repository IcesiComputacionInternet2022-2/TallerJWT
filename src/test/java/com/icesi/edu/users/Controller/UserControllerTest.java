package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;
    private UserDTO newUser;



    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService,userMapper);
    }
    public boolean haveException(){
        boolean existException = false;
        try {
            UserDTO createdUser = userController.createUser(newUser);
        }catch (RuntimeException runtimeException){
            existException = true;
        }
        return existException;
    }
    @Test
    public void registerUserSuccessfully(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+573175029108";
        String firstName = "Juan";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertFalse(haveException());
    }
    @Test
    public void registerUserNotSuccessfully(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+573175029108";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, null, null);
        assertTrue(haveException());
    }
    @Test
    public void invalidEmailDomain(){
        String email = "JuanRodriguez@u.icesi.edu.co";
        String phone = "+573175029108";
        String firstName = "Juan";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    @Test
    public void invalidEmailCharacters(){
        String email = "Juan.Rodriguez-26@icesi.edu.co";
        String phone = "+573175029108";
        String firstName = "Juan";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    @Test
    public void invalidPhoneSpaces(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+57317 029108";
        String firstName = "Juan";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    @Test
    public void invalidPhonePrefix(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+69317029108";
        String firstName = "Juan";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    @Test
    public void invalidPhoneLength(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+57317029108201";
        String firstName = "Juan";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    @Test
    public void invalidPhoneFormat(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+57317'l29108";
        String firstName = "Juan";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    @Test
    public void invalidFirstNameCharacters(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+57317029108";
        String firstName = "Juan8";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    public void invalidFirstNameLength(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+57317029108";
        String firstName = "Juannnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
        String lastName = "rodriguez";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    public void invalidLastNameLength(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+57317029108";
        String firstName = "Juan";
        String lastName = "rodriguezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    public void invalidLastNameCharacters(){
        String email = "JuanRodriguez@icesi.edu.co";
        String phone = "+57317029108";
        String firstName = "Juan";
        String lastName = "rodriguez.";
        newUser = new UserDTO(UUID.randomUUID(), email, phone, firstName, lastName);
        assertTrue(haveException());
    }
    @Test
    public void acceptGetUsersOneTime(){
        userController.getUsers();
        verify(userService, times(1)).getUsers();
    }


}
