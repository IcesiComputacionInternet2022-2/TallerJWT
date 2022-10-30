package com.icesi.edu.users.Controller;

import com.icesi.edu.users.controller.UserController;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;
    private UserCreateDTO userCreateDTO;
    private UUID uuid;


    @BeforeEach
    private void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService,userMapper);
    }

    private void scene1(){
        uuid = UUID.randomUUID();
        String email = "example1234@icesi.edu.co";
        String phoneNumber = "+573116836196";
        String firstName = "Pedro";
        String lastName = "Picapapa";
        String password = "Rorschach8041";
        userCreateDTO = new UserCreateDTO(uuid,email,phoneNumber,firstName,lastName,password);
    }

    @Test
    public void testCreateUsers(){
        scene1();
        assertTrue(createGeneratesException());
    }

    @Test
    public void testGetUsers(){
        userController.getUsers();
        verify(userService, times(1)).getUsers();
    }

    @Test
    public void testGetUser(){
        scene1();
        userController.getUser(uuid);
        verify(userService,times(1)).getUser(uuid);
    }

    private boolean createGeneratesException(){
        when(userMapper.CreateUser(any())).thenReturn(userCreateDTO);
        try {
            userController.createUser(userCreateDTO);
        }
        catch (Exception e){
            return true;
        }
        return false;
    }

    @Test
    public void testName(){
        scene1();
        userCreateDTO.setFirstName("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testLastname(){
        scene1();
        userCreateDTO.setLastName("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testCorrectName(){
        scene1();
        userCreateDTO.setFirstName("...");
        assertTrue(createGeneratesException());
    }
    @Test
    public void testCorrectLastName(){
        scene1();
        userCreateDTO.setLastName("...");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testDomain(){
        scene1();
        userCreateDTO.setEmail("example@yahoo.com");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testEmail(){
        scene1();
        userCreateDTO.setEmail("@@@@icesi.edu.co");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testCountryCode(){
        scene1();
        userCreateDTO.setPhoneNumber("3116836196");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testNoEmailAndNoPhone(){
        scene1();
        userCreateDTO.setEmail(null);
        userCreateDTO.setPhoneNumber(null);
        assertTrue(createGeneratesException());
    }



}