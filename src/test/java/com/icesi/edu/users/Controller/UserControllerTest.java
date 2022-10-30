package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTOConsult;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserMapper userMapper;
    private UserService userService;


    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService,userMapper);
    }

    @Test
    public void testGetUser(){
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "jcz@icesi.edu.co","+573107115056","Juan","Zorrilla");
        LocalDate ld = LocalDate.now();
        UserDTOConsult userDTOConsult = new UserDTOConsult(uuid, "jcz@icesi.edu.co","+573107115056","Juan","Zorrilla",ld);

        when(userService.getUser(any())).thenReturn(user);
        when(userMapper.fromUserToUserDTOConsult(any())).thenReturn(userDTOConsult);
        assertEquals(userController.getUser(userDTOConsult.getId()), userDTOConsult);
    }

    public List<UserDTO> scenary2() {
        List<UserDTO> users = new ArrayList<>();
        UserDTO currentUser = new UserDTO(UUID.randomUUID(), "juanmiloz@icesi.edu.co", "+573107115056", "Juan", "Zorrilla");
        UserDTO currentUser2 = new UserDTO(UUID.randomUUID(), "pedro@icesi.edu.co", "+573183942937", "Pedro", "Escamilla");
        UserDTO currentUser3 = new UserDTO(UUID.randomUUID(), "francisco@icesi.edu.co", "+573158223733", "Francisco", "Ramirez");
        users.add(currentUser);
        users.add(currentUser2);
        users.add(currentUser3);
        return users;
    }
    @Test
    public void testGetUsers(){
        List<User> users = new ArrayList<>();
        List<UserDTO> usersDTO = new ArrayList<>();
        UserDTO userDTO = new UserDTO();

        when(userService.getUsers()).thenReturn(users);
        when(userMapper.fromUser(any())).thenReturn(userDTO);
        assertEquals(userController.getUsers(), usersDTO);
    }
    @Test
    public void testControllerVerifyInputs() {
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", "+573107214342", "Juan", "Zorrilla");

        userController.createUser(userDTO);
        verify(userService,times(1)).createUser(any());
    }

    @Test
    public void testControllerVerifyEmptyEmailPhone() {
        UserDTO userDTO = new UserDTO(null, null, null, "Juan", "Zorrilla");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyNullEmail() {
        UserDTO userDTO = new UserDTO(null, null, "+573107214342", "Juan", "Zorrilla");

        userController.createUser(userDTO);
        verify(userService,times(1)).createUser(any());
    }

    @Test
    public void testControllerVerifyNullPhone() {
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", null, "Juan", "Zorrilla");

        userController.createUser(userDTO);
        verify(userService,times(1)).createUser(any());
    }

    @Test
    public void testControllerVerifyIncorrectDomain() {
        UserDTO userDTO = new UserDTO(null, "jcz@hotmail.com", "+573107114035", "Juan", "Zorrilla");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyIncorrectEmail() {
        UserDTO userDTO = new UserDTO(null, "katri#r@icesi.edu.co", "+573107114035", "Juan", "Zorrilla");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyIncorrectPhonePrefix() {
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", "3107214342", "Juan", "Zorrilla");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyIncorrectPhoneSpaces() {
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", "+5731072 14342", "Juan", "Zorrilla");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyIncorrectPhoneLength() {
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", "+5731072142", "Juan", "Zorrilla");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyIncorrectFirstNameLength() {
        String longStrign = "abcdefghijklmnnopqrstuvwxyzabcdefghijklmnnopqrstuvwxyzabcdefghijklmnn" +
                "opqrstuvwxyzabcdefghijklmnnopqrstuvwxyzabcdefghijklm";
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", "+573107214223", longStrign, "Zorrilla");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyIncorrectLastNameLength() {
        String longStrign = "abcdefghijklmnnopqrstuvwxyzabcdefghijklmnnopqrstuvwxyzabcdefghijklmnn" +
                "opqrstuvwxyzabcdefghijklmnnopqrstuvwxyzabcdefghijklm";
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", "+573107214293", "Juan", longStrign);
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyIncorrectFistNameFormat() {
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", "+573107214293", "Jua*n", "Zorrilla");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testControllerVerifyIncorrectLastNameFormat() {
        UserDTO userDTO = new UserDTO(null, "juanmiloz@icesi.edu.co", "+573107214293", "Juan", "Zorr#illa");
        boolean error = false;

        try {
            userController.createUser(userDTO);
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }
}
