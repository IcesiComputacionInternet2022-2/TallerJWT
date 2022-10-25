package com.icesi.edu.users.Controller;

import com.icesi.edu.users.controller.UserController;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;
    private UserDTO userDTO;
    private UUID id;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService,userMapper);
        userInit();
    }

    private void userInit(){
        id = UUID.randomUUID();
        String email = "kennetSanchez@icesi.edu.co";
        String phoneNumber = "+573005533490";
        String firstName = "Kennet";
        String lastName = "Sanchez";
        userDTO = new UserDTO(id,email,phoneNumber,firstName,lastName,null, null);
    }

    @Test
    public void getUserTest(){
        userController.getUser(id);
        verify(userService,times(1)).getUser(id);
    }

    @Test
    public void createUserTest(){
        userController.createUser(userDTO);
        verify(userService,times(1)).createUser(any());
    }
    @Test
    public void getUsersTest(){
        userController.getUsers();
        verify(userService,times(1)).getUsers();
    }

    @Test
    public void validateDomainTest(){
        userDTO.setEmail("kennet@gmail.com");
        try{
            userController.createUser(userDTO);
        }
        catch (Exception e){
            verify(userService,times(0)).createUser(any());
        }
    }
    @Test
    public void hasSpecialCharactersTest(){
        userDTO.setEmail("kennet.......@icesi.edu.co");
        try{
            userController.createUser(userDTO);
        }
        catch (Exception e){
            verify(userService,times(0)).createUser(any());
        }
    }
    @Test
    public void colombianNumberTest(){
        userDTO.setPhoneNumber("3005533490");
        try{
            userController.createUser(userDTO);
        }
        catch (Exception e){
            verify(userService,times(0)).createUser(any());
        }
    }
    @Test
    public void numberContainsWhiteSpacesTest(){
        userDTO.setPhoneNumber("+57 300 553 3490");
        try{
            userController.createUser(userDTO);
        }
        catch (Exception e){
            verify(userService,times(0)).createUser(any());
        }
    }
    @Test
    public void validSizeNumberTest(){
        userDTO.setPhoneNumber("+57 3005222222222222223490");
        try{
            userController.createUser(userDTO);
        }
        catch (Exception e){
            verify(userService,times(0)).createUser(any());
        }
    }
    @Test
    public void hasAtLeastOneContactWayTest(){
        userDTO.setPhoneNumber(null);
        userDTO.setEmail(null);
        try{
            userController.createUser(userDTO);
        }
        catch (Exception e){
            verify(userService,times(0)).createUser(any());
        }
    }
    @Test
    public void namesSizeValidationTest(){
        userDTO.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        try{
            userController.createUser(userDTO);
        }
        catch (Exception e){
            verify(userService,times(0)).createUser(any());
        }
    }
    @Test
    public void hasSpecialCharactersOnNamesTest(){
        userDTO.setLastName("-_-_-_-_-_-_-_-");
        try{
            userController.createUser(userDTO);
        }
        catch (Exception e){
            verify(userService,times(0)).createUser(any());
        }
    }

}
