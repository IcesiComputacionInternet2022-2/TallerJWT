package c.i.e.u.controller;

import com.icesi.edu.users.controller.UserController;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
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
    private UserService userService;
    private UserMapper userMapper;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void testCreateUser(){
        UserCreateDTO userCreateDTO = new UserCreateDTO(UUID.randomUUID(),"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!");
        userController.createUser(userCreateDTO);
        verify(userMapper, times(1)).fromUser(any());
    }

    @Test
    public void testGetUser(){
        UUID id = UUID.randomUUID();
        UserDTO expectedUser = new UserDTO(id,"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", LocalDate.now());
        when(userMapper.fromUser(any())).thenReturn(new UserDTO(id,"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", LocalDate.now()));
        UserDTO retrievedUser = userController.getUser(id);
        verify(userService, times(1)).getUser(any());
        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    public void testGetUserNotEquals(){
        UUID id = UUID.randomUUID();
        UserDTO notExpectedUser = new UserDTO(id,"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", LocalDate.now());
        when(userMapper.fromUser(any())).thenReturn(new UserDTO(UUID.randomUUID(),"johndoDe@icesi.edu.co", "+573164518508", "John", "Doe", LocalDate.now()));
        UserDTO retrievedUser = userController.getUser(id);
        verify(userService, times(1)).getUser(any());
        assertNotEquals(notExpectedUser, retrievedUser);
    }

    @Test
    public void testGetUsersEmpty() {
        List<User> users = new ArrayList<>();
        when(userService.getUsers()).thenReturn(users);
        assertEquals(0, userService.getUsers().size());
        verify(userService, times(1)).getUsers();
    }

    @Test
    public void testGetUsersNotEmpty() {
        List<User> users = new ArrayList<>();
        users.add(new User(UUID.randomUUID(),"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
        when(userService.getUsers()).thenReturn(users);
        assertEquals(1, userController.getUsers().size());
        verify(userService, times(1)).getUsers();
    }
}
