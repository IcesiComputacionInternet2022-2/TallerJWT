package com.icesi.edu.users.Controller;


import com.icesi.edu.users.controller.UserController;
import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersControllerTest {



    private UserService userService;

    private UserController userController;

    private UserMapper userMapper;


    @BeforeEach
    public void init(){
        userService = mock(UserServiceImpl.class);
        userMapper = new UserMapperImpl();
        userController = new UserController( userService,userMapper);
    }

    @Test
    public void testPasswordStrong(){
        UserCreateDTO userCreateDTO = new UserCreateDTO(UUID.randomUUID(),"anamaria@icesi.edu.co","+573456789076","Ana","Villada","AnaMaria.1");
            userController.createUser(userCreateDTO);
    }




}

