package com.icesi.edu.users.service;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;


    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    public User scenary1(){
        UUID id = UUID.randomUUID();
        String email = "juanmiloz@icesi.edu.co";
        String phoneNumber = "+573107114923";
        String fName = "Juan";
        String lName = "Zorrilla";
        return new User(id,email,phoneNumber,fName,lName);
    }

    public List<User> scenary2() {
        List<User> users = new ArrayList<>();
        User currentUser = new User(UUID.randomUUID(), "juanmiloz@icesi.edu.co", "+573107115056", "Juan", "Zorrilla");
        users.add(currentUser);
        return users;
    }

    public List<User> scenary3() {
        List<User> users = new ArrayList<>();
        User currentUser = new User(UUID.fromString("670b9562-b30d-52d5-b827-655787665500"), "juanmiloz@icesi.edu.co", "+573107115056", "Juan", "Zorrilla");
        User currentUser2 = new User(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "pedro@icesi.edu.co", "+573183942937", "Pedro", "Escamilla");
        User currentUser3 = new User(UUID.fromString("ba209999-0c6c-11d2-97cf-00c04f8eea45"), "francisco@icesi.edu.co", "+573158223733", "Francisco", "Ramirez");
        users.add(currentUser);
        users.add(currentUser2);
        users.add(currentUser3);
        return users;
    }

    @Test
    public void testGetUser(){
        List<User> users = scenary3();
        User user = users.get(1);

        userRepository.findById(any());
        userService.getUser(user.getId());
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void testNotGetUser(){
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertNull(userService.getUser(uuid));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void testCreateUser(){
        User user = scenary1();

        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findAll()).thenReturn(new ArrayList<User>());
        assertEquals(user, userService.createUser(user));
        verify(userRepository,times(1)).save(any());
        verify(userRepository,times(1)).findAll();
    }

    @Test
    public void testCreateUserSameEmail(){
        User newUser = new User(UUID.randomUUID(),"juanmiloz@icesi.edu.co","+573183042387","Pedro","Ramirez");

        List<User> users = scenary2();

        when(userRepository.findAll()).thenReturn(users);
        try{
            userService.createUser(newUser);
            fail();
        }catch(RuntimeException re){
            verify(userRepository, times(0)).save(any());
            verify(userRepository, times(1)).findAll();
        }
    }

    @Test
    public void testCreateUserSamePhone(){
        User newUser = new User(UUID.randomUUID(),"rms@icesi.edu.co","+573107115056","Pedro","Ramirez");

        List<User> users = scenary2();

        when(userRepository.findAll()).thenReturn(users);
        try{
            userService.createUser(newUser);
            fail();
        }catch(RuntimeException re){
            verify(userRepository, times(0)).save(any());
            verify(userRepository, times(1)).findAll();
        }
    }

    @Test
    public void testGetUsers(){
        userRepository.findAll();
        verify(userRepository, times(1)).findAll();
    }

}