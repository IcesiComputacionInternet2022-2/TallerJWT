package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.ResponseDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        userMapper = new UserMapperImpl();
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void testGetUser() {
        UserDTO userDTO = new UserDTO(null, "test@test.com", "12345678", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.getUser(any())).thenReturn(user);
        ResponseDTO responseDTO = userMapper.toResponse(user);
        assertEquals(responseDTO, userController.getUser(any()));
        verify(userService, times(1)).getUser(any());
    }

    @Test
    public void testGetUserNotFound() {
        UserDTO userDTO = new UserDTO(null, "test@test.com", "12345678", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.getUser(any())).thenReturn(null);
        assertNull(userController.getUser(user.getId()));
        verify(userService, times(1)).getUser(any());
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertEquals(userDTO, userController.createUser(userDTO));
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testCreateUserIfEmailAndPhoneNumberAreNull(){
        UserDTO userDTO = new UserDTO(null, null, null, "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'Either email or phone number must be present'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserIfEmailIsNull(){
        UserDTO userDTO = new UserDTO(null, null, "+573101231234", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertEquals(userDTO, userController.createUser(userDTO));
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testCreateUserIfPhoneNumberIsNull(){
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", null, "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertEquals(userDTO, userController.createUser(userDTO));
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidEmail(){
        UserDTO userDTO = new UserDTO(null, "test", "+573101231234", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'Invalid email'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidEmailDomain(){
        UserDTO userDTO = new UserDTO(null, "test@correo.ice.a", "+573101231234", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The domain is wrong'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidPhoneNumberPrefix() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "1234567", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The phone number must have the colombian prefix'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidPhoneNumberFormat() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+57 310a123s234", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'Invalid phone number'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidPhoneNumberLength() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+5731012312341", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'Invalid phone number'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidFirstName() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test%$@#", "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The firstName should not have special characters or numbers'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidLastName() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test", "test!@# -_");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The lastName should not have special characters or numbers'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidFirstNameLength() {

        String invalidFirstName = "a".repeat(121);

        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", invalidFirstName, "test");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The firstName should not have more than 120 characters'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidLastNameLength() {

        String invalidLastName = "a".repeat(121);

        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test", invalidLastName);
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The lastName should not have more than 120 characters'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testGetUsers() {
        UserDTO userDTO0 = new UserDTO(null, "test0@icesi.edu.co", "+573101231234", "test", "test");
        UserDTO userDTO1 = new UserDTO(null, "test1@icesi.edu.co", "+573101231233", "test", "test");
        UserDTO userDTO2 = new UserDTO(null, "test2@icesi.edu.co", "+573101231232", "test", "test");

        List<UserDTO> users = List.of(userDTO0, userDTO1, userDTO2);

        List<User> usersList = users.stream().map(userMapper::fromDTO).collect(Collectors.toList());
        when(userService.getUsers()).thenReturn(usersList);

        assertEquals(users, userController.getUsers());

        verify(userService, times(1)).getUsers();

    }
}
