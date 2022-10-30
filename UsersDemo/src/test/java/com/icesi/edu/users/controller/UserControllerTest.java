package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTORequest;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserService userService;
    private UserMapper userMapper;
    private UserController userController;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void testGetUser(){
        UUID id = UUID.randomUUID();
        String requestDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        UserDTORequest expectedDTO = new UserDTORequest(id, "juca@icesi.edu.co", "+573186215574", "Juca", "Ota", requestDate);

        when(userMapper.fromUserToDTORequest(any())).thenReturn(expectedDTO);
        when(userService.getUser(any())).thenReturn(new User(id, "juca@icesi.edu.co", "+573186215574", "Juca", "Ota"));

        userController.getUser(id);
        verify(userMapper, times(1)).fromUserToDTORequest(any());
        verify(userService, times(1)).getUser(any());
    }

    @Test
    public void testGetNotSavedUser(){
        UUID id = UUID.randomUUID();
        String requestDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        UserDTORequest expectedDTO = new UserDTORequest();
        expectedDTO.setRequestDate(requestDate);

        when(userMapper.fromUserToDTORequest(any())).thenReturn(expectedDTO);
        when(userService.getUser(any())).thenReturn(null);

        userController.getUser(id);
        verify(userMapper, times(1)).fromUserToDTORequest(any());
        verify(userService, times(1)).getUser(any());
    }

    @Test
    public void testCreateUser(){
        UUID id = UUID.randomUUID();
        UserDTO userDTO = new UserDTO(id, "jota@icesi.edu.co", "+573186215574", "Juca", "Ota");
        User user = new User(id, "jota@icesi.edu.co", "+573186215574", "Juca", "Ota");

        when(userMapper.fromUser(any())).thenReturn(userDTO);
        when(userMapper.fromDTO(any())).thenReturn(user);
        when(userService.createUser(any())).thenReturn(user);

        try {
            userController.createUser(userDTO);
            verify(userMapper, times(1)).fromUser(any());
            verify(userMapper, times(1)).fromDTO(any());
            verify(userService, times(1)).createUser(any());

        } catch (RuntimeException exception) {
            fail();
        }

    }

    @Test
    public void testCreateUserWrongDomain(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@gmail.com", "+573186215574", "Juca", "Ota");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserNotValidEmail(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "", "+573186215574", "Juca", "Ota");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserWithSpecialChars(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "+jota@icesi.edu.co", "+573186215574", "Juca", "Ota");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserWrongPhoneNumberDomain(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@icesi.edu.co", "+623186215574", "Juca", "Ota");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserSpacesInPhoneNumber(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@icesi.edu.co", "+57 3186215574", "Juca", "Ota");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserPhoneNumberWrongFormat(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@icesi.edu.co", "+57318621557499", "Juca", "Ota");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserFirstNameExceedsMaxSize(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@icesi.edu.co", "+573186215574",
                "Jaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "Ota");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserLastNameExceedsMaxSize(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@icesi.edu.co", "+573186215574",
                "Juca",
                "Jaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserFirstTameContainsSpecialChars(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@icesi.edu.co", "+573186215574",
                "Juca1.3",
                "Ota");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserLastNameContainsSpecialChars(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@icesi.edu.co", "+573186215574", "Juca", "Ota1.2");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserNullEmail(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), null, "+573186215574", "Juca", "Ota1.2");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserNullPhoneNumber(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "jota@icesi.edu.co", null, "Juca", "Ota1.2");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }

    @Test
    public void testCreateUserNullPhoneNumberAndEmail(){

        UserDTO userDTO = new UserDTO(UUID.randomUUID(), null, null, "Juca", "Ota1.2");

        try{
            userController.createUser(userDTO);
            fail();
        }catch (RuntimeException exception){
            verify(userMapper, times(0)).fromUser(any());
            verify(userMapper, times(0)).fromDTO(any());
            verify(userService, times(0)).createUser(any());
        }

    }



    @Test
    public void testGetUsers(){
        List<User> serviceUsers = new ArrayList<>();
        List<UserDTO> controllerUsers = new ArrayList<>();

        when(userService.getUsers()).thenReturn(serviceUsers);
        when(userMapper.fromUser(any())).thenReturn(new UserDTO());

        userController.getUsers();
        verify(userService, times(1)).getUsers();
        verify(userMapper, times(serviceUsers.size())).fromUser(any());
    }

}
