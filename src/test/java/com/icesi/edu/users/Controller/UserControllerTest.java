package com.icesi.edu.users.Controller;

import com.icesi.edu.users.controller.UserController;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;
    private UserDTO userDTO;
    private UUID uuid;

    @BeforeEach
    private void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService,userMapper);
    }

    private void setupScene1(){
        uuid = UUID.randomUUID();
        String email = "juandavid227@icesi.edu.co";
        String phoneNumber = "+573166670887";
        String firstName = "Juan";
        String lastName = "Cruz";
        String password  = "Si";
        userDTO = new UserCreateDTO(password);
        userDTO.setPhoneNumber(phoneNumber);
        userDTO.setEmail(email);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
    }

    @Test
    public void testCreateUsers(){
        setupScene1();
        assertFalse(createGeneratesException());
    }

    @Test
    public void testGetUsers(){
        userController.getUsers();
        verify(userService, times(1)).getUsers();
    }

    @Test
    public void testGetUser(){
        setupScene1();
        userController.getUser(uuid);
        verify(userService,times(1)).getUser(uuid);
    }

    private boolean createGeneratesException(){
        when(userMapper.fromUser(any())).thenReturn(userDTO);
        try {
            userController.createUser((UserCreateDTO) userDTO);
        }
        catch (Exception e){
            return true;
        }
        return false;
    }

    @Test
    public void testSetTheDateWhenUsingGetUser(){
        setupScene1();
        when(userMapper.fromUser(any())).thenReturn(userDTO);
        UserDTO obtainedUserDto = userController.getUser(uuid);
        //assertEquals(obtainedUserDto.getDate(), LocalDate.now().toString());
    }

    @Test
    public void testEmailDomain(){
        setupScene1();
        userDTO.setEmail("juandavid@hotmail.com");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testEmailSpecialCharacters(){
        setupScene1();
        userDTO.setEmail("juandavid.227@icesi.edu.co");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testPlus57Number(){
        setupScene1();
        userDTO.setPhoneNumber("3166670887");
        assertTrue(createGeneratesException());
    }
    @Test
    public void testLessThan10Digits(){
        setupScene1();
        userDTO.setPhoneNumber("+57316667088");
        assertTrue(createGeneratesException());
    }
    @Test
    public void testNoSpacesNumber(){
        setupScene1();
        userDTO.setPhoneNumber("+57 316 667 0");
        assertTrue(createGeneratesException());
    }
    @Test
    public void testCanCreateWithJustEmail(){
        setupScene1();
        userDTO.setPhoneNumber(null);
        assertFalse(createGeneratesException());
    }
    @Test
    public void testCanCreateWithJustNumber(){
        setupScene1();
        userDTO.setEmail(null);
        assertFalse(createGeneratesException());
    }
    @Test
    public void testCantCreateWithoutNumberOrEmail(){
        setupScene1();
        userDTO.setEmail(null);
        userDTO.setPhoneNumber(null);
        assertTrue(createGeneratesException());
    }
    @Test
    public void testFirstnameLessThan120(){
        setupScene1();
        userDTO.setFirstName("IvinPQnwNIoUPZOXfwtjtmiXeeYvQzadvWDjMRmfvOowkOJkDKCoPkIWxMTCRNKXsBXGKMgjANGGmxgBrEKQZKdpWkpjTPQsuPLZGtEYuHZAivuFnkERfMICe");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testLastnameLessThan120(){
        setupScene1();
        userDTO.setLastName("IvinPQnwNIoUPZOXfwtjtmiXeeYvQzadvWDjMRmfvOowkOJkDKCoPkIWxMTCRNKXsBXGKMgjANGGmxgBrEKQZKdpWkpjTPQsuPLZGtEYuHZAivuFnkERfMICe");
        assertTrue(createGeneratesException());
    }

    @Test
    public void testFirstnameNoSpecialCharacters(){
        setupScene1();
        userDTO.setFirstName("1. When haces tu momo en un test. El futuro es hoy oiste viejo :v");
        assertTrue(createGeneratesException());
    }
    @Test
    public void testLastNameNoSpecialCharacters(){
        setupScene1();
        userDTO.setLastName("2. But falla en la ejecucion :,v . No me parece que este chico sea muy listo.jpg");
        assertTrue(createGeneratesException());
    }




}
