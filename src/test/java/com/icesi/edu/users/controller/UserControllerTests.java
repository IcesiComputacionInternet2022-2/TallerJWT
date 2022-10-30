package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;

    private UUID USER_ID = UUID.fromString("c0a80101-0000-0000-0000-000000000000");

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        userMapper = new UserMapperImpl();
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void testGetUser() {
        User user = User.builder().build();
        when(userService.getUser(USER_ID)).thenReturn(user);
        userController.getUser(USER_ID);
        verify(userService, times(1)).getUser(USER_ID);
    }

    @Test
    public void testGetUserNotFound() {
        when(userService.getUser(USER_ID)).thenReturn(null);
        userController.getUser(USER_ID);
        verify(userService, times(1)).getUser(USER_ID);
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(any())).thenReturn(user);
        userController.createUser(userDTO);
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testCreateUserIfEmailAndPhoneNumberAreNull() {
        UserDTO userDTO = new UserDTO(null, null, null, "test", "test", "", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(UserDemoException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'Either email or phone number must be present'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserIfEmailIsNull() {
        UserDTO userDTO = new UserDTO(null, null, "+571231234123", "test", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(user)).thenReturn(user);
        userController.createUser(userDTO);
        verify(userService, times(1)).createUser(user);
    }

    @Test
    public void testCreateUserIfPhoneNumberIsNull() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", null, "test", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        when(userService.createUser(user)).thenReturn(user);
        userController.createUser(userDTO);
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        UserDTO userDTO = new UserDTO(null, "test", "+573101231234", "test", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(UserDemoException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'Invalid email'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidEmailDomain() {
        UserDTO userDTO = new UserDTO(null, "test@correo.ice.a", "+573101231234", "test", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(UserDemoException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The domain is wrong'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidPhoneNumberPrefix() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "1234567", "test", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(UserDemoException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The phone number must have the colombian prefix'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidPhoneNumberFormat() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+57 310a123s234", "test", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(UserDemoException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'Invalid phone number'");
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidPhoneNumberLength() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+5731012312341", "test", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(UserDemoException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'Invalid phone number'");
        verify(userService, times(0)).createUser(user);
    }

    @Test
    public void testCreateUserWithInvalidFirstName() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test%$@#", "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The firstName should not have special characters or numbers'");
        verify(userService, times(0)).createUser(user);
    }

    @Test
    public void testCreateUserWithInvalidLastName() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test", "test!@# -_", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The lastName should not have special characters or numbers'");
        verify(userService, times(0)).createUser(user);
    }

    @Test
    public void testCreateUserWithInvalidFirstNameLength() {

        String invalidFirstName = "a".repeat(121);

        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", invalidFirstName, "test", "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The firstName should not have more than 120 characters'");
        verify(userService, times(0)).createUser(user);
    }

    @Test
    public void testCreateUserWithInvalidLastNameLength() {

        String invalidLastName = "a".repeat(121);

        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test", invalidLastName, "10-09-2020", "");
        User user = userMapper.fromDTO(userDTO);
        assertThrows(RuntimeException.class, () -> userController.createUser(userDTO), "Should throw an exception with the message 'The lastName should not have more than 120 characters'");
        verify(userService, times(0)).createUser(user);
    }

    @Test
    public void testGetUsers() {
        UserDTO userDTO0 = new UserDTO(null, "test0@icesi.edu.co", "+573101231234", "test", "test", "10-09-2020", "");
        UserDTO userDTO1 = new UserDTO(null, "test1@icesi.edu.co", "+573101231233", "test", "test", "10-09-2020", "");
        UserDTO userDTO2 = new UserDTO(null, "test2@icesi.edu.co", "+573101231232", "test", "test", "10-09-2020", "");

        List<UserDTO> users = List.of(userDTO0, userDTO1, userDTO2);
        List<User> usersList = users.stream().map(userMapper::fromDTO).collect(Collectors.toList());
        when(userService.getUsers()).thenReturn(usersList);

        userController.getUsers();

        verify(userService, times(1)).getUsers();

    }
}
