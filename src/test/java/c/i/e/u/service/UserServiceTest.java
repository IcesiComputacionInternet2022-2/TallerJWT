package c.i.e.u.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testCreateUser(){
        UUID id = UUID.randomUUID();
        userService.createUser(new User(id,"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
        verify(userRepository, times(1)).save(any());
        assertNotNull(userRepository.findById(id));
    }

    @Test
    public void testCreateUserDuplicatedId(){
        UUID id = UUID.randomUUID();
        when(userRepository.save(any())).thenReturn(new User(id,"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
        try {
            userService.createUser(new User(id,"johndoe@icesi.edu.co", "+570000000000", "John", "Doe", "passworD1!"));
        } catch(RuntimeException runtimeException) {
            verify(userRepository, times(1)).save(any());
            assertNull(userRepository.findById(id));
        }
    }

    @Test
    public void testCreateUserDuplicatedEmail(){
        UUID id = UUID.randomUUID();
        when(userRepository.save(any())).thenReturn(new User(UUID.randomUUID(),"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
        try {
            userService.createUser(new User(id,"johndoe@icesi.edu.co", "+570000000000", "John", "Doe", "passworD1!"));
        } catch(RuntimeException runtimeException) {
            verify(userRepository, times(1)).save(any());
            assertNull(userRepository.findById(id));
        }
    }

    @Test
    public void testCreateUserDuplicatedPhoneNumber(){
        try {
            when(userRepository.save(any())).thenReturn(new User(UUID.randomUUID(),"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
            userService.createUser(new User(UUID.randomUUID(),"aaa@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
        } catch(RuntimeException runtimeException) {
            verify(userRepository, times(1)).save(any());
            assertEquals(1, userService.getUsers().size());
        }
    }

    @Test
    public void testCreateUserDuplicatedEmailAndPhoneNumber(){
        try {
            when(userRepository.save(any())).thenReturn(new User(UUID.randomUUID(),"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
            userService.createUser(new User(UUID.randomUUID(),"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
        } catch(RuntimeException runtimeException) {
            verify(userRepository, times(1)).save(any());
            assertEquals(1, userService.getUsers().size());
        }
    }

    @Test
    public void testGetUser(){
        UUID id = UUID.randomUUID();
        User expectedUser = new User(id,"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!");
        when(userRepository.findById(any())).thenReturn(Optional.of(new User(id,"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!")));
        User retrievedUser = userService.getUser(id);
        verify(userRepository, times(1)).findById(any());
        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    public void testGetUserNotEquals(){
        UUID id = UUID.randomUUID();
        User notExpectedUser = new User(id,"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!");
        when(userRepository.findById(any())).thenReturn(Optional.of(new User(UUID.randomUUID(),"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!")));
        User retrievedUser = userService.getUser(id);
        verify(userRepository, times(1)).findById(any());
        assertNotEquals(notExpectedUser, retrievedUser);
    }

    @Test
    public void testGetUsersEmpty() {
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(0, userService.getUsers().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUsersNotEmpty() {
        List<User> users = new ArrayList<>();
        users.add(new User(UUID.randomUUID(),"johndoe@icesi.edu.co", "+573164518508", "John", "Doe", "passworD1!"));
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(1, userService.getUsers().size());
        verify(userRepository, times(1)).findAll();
    }
}
