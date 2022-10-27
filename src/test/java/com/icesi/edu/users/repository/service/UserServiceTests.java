package com.icesi.edu.users.repository.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    public User scenario(String email, String phoneNumber, String firstName, String lastName) {
        User user = new User();
        user.generateId();
        user.setPhoneNumber(phoneNumber);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }

    @Test
    public void testCreateUser() {
        User user = scenario("test@test.com", "123456789", "test", "TEST");
        userService.createUser(user);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testCreateUserRepeatedEmail() {

        User user = scenario("test@test.com", "123456789", "test", "TEST");
        User user2 = scenario("test@test.com", "123456781", "test1", "TEST1");
        userService.createUser(user);
        when(userRepository.findAll()).thenReturn(List.of(user));
        assertThrows(RuntimeException.class, () -> userService.createUser(user2), "Should throw an exception with the message 'The email is already in use'");
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testCreateUserRepeatedPhone() {
        User user = scenario("test@test.com", "123456789", "test", "TEST");
        User user2 = scenario("test2@test.com", "123456789", "test1", "TEST1");
        userService.createUser(user);
        when(userRepository.findAll()).thenReturn(List.of(user));
        assertThrows(RuntimeException.class, () -> userService.createUser(user2), "Should throw an exception with the message 'The phone number is already in use'");
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testGetUser() {
        User user = scenario("test@test.com", "123456789", "test", "TEST");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertEquals(user, userService.getUser(user.getId()));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void testGetUserIfDoesntExists() {
        User user = scenario("test@test.com", "123456789", "test", "TEST");
        when(userRepository.findById(UUID.randomUUID())).thenReturn(null);
        assertNull(userService.getUser(user.getId()));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void testGetUsers() {
        User user0 = scenario("test@test.com", "123456789", "test", "TEST");
        User user1 = scenario("test1@test.com", "123456788", "test", "TEST");
        User user2 = scenario("test2@test.com", "123456787", "test", "TEST");
        List<User> users = List.of(user0, user1, user2);
        when(userRepository.findAll()).thenReturn(users);
        users = users.stream().peek(u -> {
            String ID = u.getId().toString();
            u.setId(UUID.fromString("00000000-0000-0000-0000-00000000" + ID.substring(ID.length() - 4)));
        }).collect(Collectors.toList());
        assertEquals(users, userService.getUsers());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUsersIfEmpty() {
        when(userRepository.findAll()).thenReturn(List.of());
        assertEquals(List.of(), userService.getUsers());
        verify(userRepository, times(1)).findAll();
    }
}
