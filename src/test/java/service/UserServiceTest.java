package service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class UserServiceTest {
    private  UserRepository userRepository;
    private UserService userService;
    private UUID id;
    private User user;

    @BeforeEach
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void getUserTest() {
        exampleUser();
        userService.createUser(user);
        userService.getUser(id);
        verify(userRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void getUserLastTimeSearchedTest(){
        exampleUser();
        userService.createUser(user);
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.getUser(id).getLastTimeSearched(), LocalDate.now());
    }

    public void exampleUser(){
        id = UUID.randomUUID();
        String email = "kennetSanchez@icesi.edu.co";
        String phoneNumber = "+573005533490";
        String firstName = "Kennet";
        String lastName = "Sanchez";
        user = new User(id,email,phoneNumber,firstName,lastName,null, null);
    }

    @Test
    public void createUserTest(){
        exampleUser();
        assertFalse(checkRepitition()); //Can create without any problem
    }

    @Test
    public void getUsersTest(){
        //Doesn't mather if the list is empty
        userService.getUsers();
        verify(userRepository,times(1)).findAll();
    }

    private boolean checkRepitition(){
        try{
            userService.createUser(user);
        }
        catch (Exception e){
            return true;
        }
        return false;
    }

    @Test
    public void repitedPhoneOrEmailTest(){
        exampleUser();
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        List<User> userList = new ArrayList<>();
        userList.add(userService.createUser(user));
        when(userService.getUsers()).thenReturn(userList);
        assertTrue(checkRepitition());
    }
}
