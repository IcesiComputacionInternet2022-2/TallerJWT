package com.icesi.edu.users.service;

import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.security.SecurityContext;
import com.icesi.edu.users.security.SecurityContextHolder;
import com.icesi.edu.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceTest {
	
	private final static UUID ID = UUID.fromString("94cd0d26-79b6-44ef-acc7-58792bd9b3a6");
	private final static String EMAIL = "carlosjpantoja@icesi.edu.co";
	private final static String PHONE = "+573135965423";
	private final static String FNAME = "Carlos";
	private final static String LNAME = "Pantoja";

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
	public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

	@Test
	public void testGetUser() {
		User baseUser = baseUser();
		when(userRepository.findById(baseUser.getId())).thenReturn(Optional.of(baseUser));
		SecurityContextHolder.getContext().setUserId(baseUser.getId());
		User userResult = userService.getUser(baseUser.getId());
		verify(userRepository, times(1)).findById(baseUser.getId());
		assertNotNull(userResult);
		assertEquals(userResult.getId(), ID);
		assertEquals(userResult.getEmail(), EMAIL);
		assertEquals(userResult.getPhoneNumber(), PHONE);
		assertEquals(userResult.getFirstName(), FNAME);
		assertEquals(userResult.getLastName(), LNAME);
	}

    @Test
    public void testCreateUser() {
    	User baseUser = baseUser();
    	when(userRepository.save(any())).thenReturn(baseUser);
    	User userResult = userService.createUser(baseUser);
    	verify(userRepository, times(1)).save(any());
    	assertNotNull(userResult);
    	assertEquals(userResult.getId(), ID);
    	assertEquals(userResult.getEmail(), EMAIL);
    	assertEquals(userResult.getPhoneNumber(), PHONE);
    	assertEquals(userResult.getFirstName(), FNAME);
    	assertEquals(userResult.getLastName(), LNAME);
    }
    
    @Test
	public void testCreateUserWithRepeatEmail() {
		try {
			User baseUser = baseUser();
			when(userRepository.findByEmail(any())).thenReturn(Optional.of(baseUser));
			userService.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userRepository, times(1)).findByEmail(any());
		}
	}
    
    @Test
	public void testCreateUserWithRepeatPhoneNumber() {
		try {
			User baseUser = baseUser();
			when(userRepository.findByPhoneNumber(any())).thenReturn(Optional.of(baseUser));
			userService.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userRepository, times(1)).findByPhoneNumber(any());
		}
	}

	@Test
	public void testGetUsers() {
		User baseUser = baseUser();
		when(userRepository.findAll()).thenReturn(List.of(baseUser));
		List<User> userResult = userService.getUsers();
		verify(userRepository, times(1)).findAll();
		assertNotNull(userResult.get(0));
		assertEquals(userResult.get(0).getId(), ID);
		assertEquals(userResult.get(0).getEmail(), EMAIL);
		assertEquals(userResult.get(0).getPhoneNumber(), PHONE);
		assertEquals(userResult.get(0).getFirstName(), FNAME);
		assertEquals(userResult.get(0).getLastName(), LNAME);
	}
    
    private User baseUser() {
		return User.builder()
						.id(ID)
						.email(EMAIL)
						.phoneNumber(PHONE)
						.firstName(FNAME)
						.lastName(LNAME)
						.build();
	}
    
}
