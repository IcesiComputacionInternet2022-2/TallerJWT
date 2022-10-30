package com.icesi.edu.users.controller;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;

    private UserDTO user;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        userMapper = new UserMapperImpl();
        userController = new UserController(userService, userMapper);
    }

    /*
     *  Scenario of a valid user with correct data to be adapted in the following tests.
     * */
    public void setupScenary1(){

        String email = "nicolasp@icesi.edu.co";
        String phoneNumber = "+573166017116";
        String name = "Nicolas";
        String lastName = "Penagos";

        user = new UserDTO(UUID.randomUUID(), email, phoneNumber, name, lastName,"");
    }

    /*
     *  This method helps us to know whether user was created or not by detecting the exception thrown.
     * */
    public boolean exceptionThrown(){

        boolean thrown = false;

        try {
            UserDTO createdUser = userController.createUser(user);
        }catch (UserDemoException e){
            System.out.println(e.getError().getMessage());
            thrown = true;

        }

        return thrown;
    }

    @Test
    public void getUserTest(){


        // getUser(UUID id) from userService has been called at 1 time
        //verify(userService, times(1)).getUser(any());
        try{
            userController.getUser(UUID.fromString("123e4567-e89b-42d3-a456-556642440000"));
        }catch (NoSuchElementException e){

            assertNotNull(e);
            //This means that auth validation was invoked
            assertEquals(e.getMessage(), "No value present");
        }

    }

    @Test
    public void getUsersTest(){

        userController.getUsers();
        // getUsers() from userService has been called at 1 time
        verify(userService, times(1)).getUsers();

    }


    @Test
    public void validateUserOkTest(){

        setupScenary1(); // an ok user
        assertFalse(exceptionThrown());

    }

    @Test
    public void validateWrongEmailDomainTest(){

        setupScenary1();
        user.setEmail("nicolasp@icesi.edu.com"); // Setting a wrong email domain
        assertTrue(exceptionThrown());

    }

    @Test
    public void validateWrongEmailDirTest(){

        setupScenary1();
        user.setEmail("nicolas.p@icesi.edu.con"); // Setting a wrong email dir
        assertTrue(exceptionThrown());

    }

    @Test
    public void validateWrongPhoneNumberCountryCodeTest(){

        setupScenary1();
        user.setPhoneNumber("+523166017116"); // Setting a  wrong country code
        assertTrue(exceptionThrown());

    }

    @Test
    public void validateWrongPhoneNumberFormatTest(){

        setupScenary1();
        user.setPhoneNumber("+52316 6017116"); // Setting a wrong phone number format
        assertTrue(exceptionThrown());

    }

    @Test
    public void validateWrongPhoneNumberLengthTest(){

        setupScenary1();
        user.setPhoneNumber("+523167116"); // Setting a shorter length
        assertTrue(exceptionThrown());


    }

    @Test
    public void validateJustEmailPresentTest(){

        setupScenary1();
        user.setEmail(null); // Setting a null email with a not null phone number
        assertFalse(exceptionThrown());

    }

    @Test
    public void validateJustPhonePresentTest(){

        setupScenary1();
        user.setPhoneNumber(null); // Setting a null phone number with a not null email
        assertFalse(exceptionThrown());

    }

    @Test
    public void validateNameLenghtTest(){
        setupScenary1();
        // Setting a first name longer than 120 characters
        user.setFirstName("Nicolasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        assertTrue(exceptionThrown());
    }

    @Test
    public void validateLastNameLenghtTest(){
        setupScenary1();
        // Setting a last name longer than 120 characters
        user.setLastName("Penagossssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        assertTrue(exceptionThrown());
    }

    @Test
    public void validateNameFormatTest(){
        setupScenary1();
        user.setFirstName("Nicolas.");   // Setting a wrong name format
        assertTrue(exceptionThrown());

    }

    @Test
    public void validateLastNameFormatTest(){
        setupScenary1();
        user.setFirstName("Penagos2");   // Setting a wrong last name format
        assertTrue(exceptionThrown());

    }


}
