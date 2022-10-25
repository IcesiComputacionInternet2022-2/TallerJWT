package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.constants.ErrorCode;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;

    private UserController userController;


    @BeforeEach
    public void init(){
        userService = mock(UserServiceImpl.class);
        UserMapper userMapper = new UserMapperImpl();
        userController = new UserController(userService, userMapper);

        when(userService.createUser(ArgumentMatchers.any())).thenReturn(new User(null,"example@u.icesi.edu.co","+573161234567","Pepito","Perez"));

    }

    @Test
    public void testCreateUser(){
        UserDTO testUser = new UserDTO(null,"example@icesi.edu.co","+573161234567","Pepito","Perez",null);
        assertEquals(testUser.getPhoneNumber(),userController.createUser(testUser).getPhoneNumber());
    }

    @Test
    public void testGetUser(){
        when(userService.getUser(ArgumentMatchers.any())).thenReturn(new User(null,"example@u.icesi.edu.co","+573161234567","Pepito","Perez"));

        assertEquals("+573161234567",userController.getUser(null).getPhoneNumber());

    }

    @Test
    public void testGetUsers(){
        when(userService.getUsers()).thenReturn(new ArrayList<>());

        assertTrue(userController.getUsers().isEmpty());
    }

    @Test
    public void testVerifyFirstNameLengthMoreThan0(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("");

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                    userController.createUser(userDTO)
                , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());


    }

    @Test
    public void testVerifyFirstNameLengthLessThan120(){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("a".repeat(121));

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                    userController.createUser(userDTO)
                , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyFirstNameValidCharacters(){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("Pe--it&^_");

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                    userController.createUser(userDTO)
                , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyLastNameLengthAtLeast0(){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("Juan");
        userDTO.setLastName("");

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                                userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyLastNameLengthLessThan120(){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("Juan");
        userDTO.setLastName("a".repeat(121));

        UserDemoException thrown =
                assertThrows(UserDemoException.class, () ->
                                userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyLastNameValidCharacters(){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName("Juan");
        userDTO.setLastName("Pe--it&^_");

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                            () -> userController.createUser(userDTO)
                            , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyContactInfoBothNull(){
        UserDTO userDTO = new UserDTO(null,null,null,"Juan","Perez",null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyContactInfoEmailNull(){
        UserDTO userDTO = new UserDTO(null,null,"+573161234567","Juan","Perez",null);

        userController.createUser(userDTO);

        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyContactInfoPhoneNumberNull(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co",null,"Juan","Perez",null);

        userController.createUser(userDTO);

        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyContactInfoBothPresent(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+573161234567","Juan","Perez",null);

        userController.createUser(userDTO);
        //Pass, no exception is thrown
    }

    @Test
    public void testVerifyEmailFormat(){
        UserDTO userDTO = new UserDTO(null,"exa mple@ices@i.edu.co","+573161234567","Juan","Perez",null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyEmailDomain(){
        UserDTO userDTO = new UserDTO(null,"example@univalle.edu.co","+573161234567","Juan","Perez",null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyEmailValidCharacters(){
        UserDTO userDTO = new UserDTO(null,"ex#$%am ple@icesi.edu.co","+573161234567","Juan","Perez",null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyPhoneNumberLength(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+57316123","Juan","Perez",null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyPhoneNumberCountryCode(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+633161234567","Juan","Perez",null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }

    @Test
    public void testVerifyPhoneNumberValidCharacters(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+57316$%34567","Juan","Perez",null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());

    }

    @Test
    public void testVerifyPhoneNumberSpaces(){
        UserDTO userDTO = new UserDTO(null,"example@icesi.edu.co","+573161 34567","Juan","Perez",null);

        UserDemoException thrown =
                assertThrows(UserDemoException.class,
                        () -> userController.createUser(userDTO)
                        , "RuntimeException expected");

        assertEquals(ErrorCode.CODE_01, thrown.getError().getCode());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
        assertEquals(ErrorCode.CODE_01.getMessage(), thrown.getError().getMessage());
    }


}
