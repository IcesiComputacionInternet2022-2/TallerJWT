package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        UserMapper userMapper = new UserMapperImpl();
        userController = new UserController(userService, userMapper);

        when(userService.createUser(any())).thenReturn(
                new User(null, "mock@icesi.edu.co", "+573153823657", "Mock", "Mockery", null));
    }

    @Test
    @SneakyThrows
    public void testController() {
        UserDTO userDTO = new UserDTO(null,"mock@icesi.edu.co", "+573153823657", "Mock", "Mockery", null);
        assertEquals(userDTO.getEmail(), userController.createUser(userDTO).getEmail());
    }

    @Test
    public void testGetUser() {
        when(userService.getUser(any())).thenReturn(new User(null, "mock@icesi.edu.co", "+573153823657", "Mock", "Mockery", null));
        assertEquals("mock@icesi.edu.co", userController.getUser(null).getEmail());
    }

    @Test
    public void testGetUsers() {
        when(userService.getUsers()).thenReturn(new ArrayList<User>());
        assertTrue(userController.getUsers().isEmpty());
    }

    @Test
    public void testValidDomainInvalid() {
        String email = "invalid@domain.com";
        UserDTO invalidUser = new UserDTO(null, email, "+573153823657", "Mock", "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
           userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testValidDomainTrick() {
        String email = "inv@lid@icesi.edu.com.co.org.gov";
        UserDTO invalidUser = new UserDTO(null, email, "+573153823657", "Mock", "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testValidEmail() {
        String email = "in<v@lid$%&@icesi.edu.c>|¬°{}^om";
        UserDTO invalidUser = new UserDTO(null, email, "+573153823657", "Mock", "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testValidCountryCode() {
        String phone = "+444023608954";
        UserDTO invalidUser = new UserDTO(null, "mock@icesi.edu.co", phone, "Mock", "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testValidPhoneNumberLength() {
        String phone = "+5731581539680";
        UserDTO invalidUser = new UserDTO(null, "mock@icesi.edu.co", phone, "Mock", "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testValidPhoneNumber() {
        String phone = "Mas cincuenta y siete tres quince tres ochenta y dos treinta y seis sesenta y ocho";
        UserDTO invalidUser = new UserDTO(null, "mock@icesi.edu.co", phone, "Mock", "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testEmailOrPhonePresent() {
        UserDTO invalidUser = new UserDTO(null, null, null, "Mock", "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testEmailOrPhoneNotEmpty() {
        UserDTO invalidUser = new UserDTO(null, "", "", "Mock", "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testNameLength() {
        String faultyName =
                "Este nombre tiene mas de ciento veinte caracteres y por ende es demasiado largo para ser valido, deberia generar un error";
        UserDTO invalidUser = new UserDTO(null, "mock@icesi.edu.co", "+573153823657", faultyName, "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testLastNameLength() {
        String faultyLastName =
                "Este nombre tiene mas de ciento veinte caracteres y por ende es demasiado largo para ser valido, deberia generar un error";
        UserDTO invalidUser = new UserDTO(null, "mock@icesi.edu.co", "+573153823657", "Mock", faultyLastName, null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testNormalName() {
        String faultyName = "/'Mock¿-.|91";
        UserDTO invalidUser = new UserDTO(null, "mock@icesi.edu.co", "+573153823657", faultyName, "Mockery", null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }

    @Test
    public void testNormalLastName() {
        String faultyLastName = "/Mock-¿'.|73";
        UserDTO invalidUser = new UserDTO(null, "mock@icesi.edu.co", "+573153823657", "Mock", faultyLastName, null);

        RuntimeException invalidDomain = assertThrows(RuntimeException.class, () -> {
            userController.createUser(invalidUser);
        }, "Invalid user creation attempt. Runtime exception is expected.");

        assertEquals(invalidDomain.getMessage(), "Invalid data");
    }
}
