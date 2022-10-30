package com.icesi.edu.users.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import com.icesi.edu.users.dto.UserCreateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.mapper.UserMapperImpl;
import com.icesi.edu.users.service.UserService;

public class UserControllerTest {
	
	private final static UUID ID = UUID.fromString("94cd0d26-79b6-44ef-acc7-58792bd9b3a6");
	private final static String EMAIL = "carlosjpantoja@icesi.edu.co";
	private final static String PHONE = "+573135965423";
	private final static String FNAME = "Carlos";
	private final static String LNAME = "Pantoja";
	private final static String PASS = "C4rl@5";
	
	private UserController userController;
    private UserService userService;
    private UserMapper userMapper;

    @BeforeEach
    public void init(){
    	userMapper = new UserMapperImpl();
    	userService = mock(UserService.class);
    	userController = new UserController(userService, userMapper);
    }

	@Test
	public void testGetUser() {
		UserCreateDTO baseUser = baseUser();
		when(userService.getUser(any())).thenReturn(userMapper.fromDTO(baseUser));
		UserCreateDTO userResult = userController.getUser(baseUser.getId());
		verify(userService, times(1)).getUser(any());
		assertNotNull(userResult);
		assertEquals(userResult.getId(), ID);
		assertEquals(userResult.getEmail(), EMAIL);
		assertEquals(userResult.getPhoneNumber(), PHONE);
		assertEquals(userResult.getFirstName(), FNAME);
		assertEquals(userResult.getLastName(), LNAME);
	}

    @Test
    public void testCreateUser() {
    	UserCreateDTO baseUser = baseUser();
    	when(userService.createUser(any())).thenReturn(userMapper.fromDTO(baseUser));
		UserCreateDTO userResult = userController.createUser(baseUser);
    	verify(userService, times(1)).createUser(any());
    	assertNotNull(userResult);
    	assertEquals(userResult.getId(), ID);
    	assertEquals(userResult.getEmail(), EMAIL);
    	assertEquals(userResult.getPhoneNumber(), PHONE);
    	assertEquals(userResult.getFirstName(), FNAME);
    	assertEquals(userResult.getLastName(), LNAME);
    }
	
	@Test
	public void testCreateUserWithoutEmail() {
		UserCreateDTO baseUser = baseUser();
		baseUser.setEmail(null);
		when(userService.createUser(any())).thenReturn(userMapper.fromDTO(baseUser));
		UserCreateDTO userReturn = userController.createUser(baseUser);
		verify(userService, times(1)).createUser(any());
		assertNotNull(userReturn);
		assertEquals(userReturn.getId(), ID);
		assertNull(userReturn.getEmail());
		assertEquals(userReturn.getPhoneNumber(), PHONE);
		assertEquals(userReturn.getFirstName(), FNAME);
		assertEquals(userReturn.getLastName(), LNAME);
	}
	
	@Test
	public void testCreateUserWithoutPhoneNumber() {
		UserCreateDTO baseUser = baseUser();
		baseUser.setPhoneNumber(null);
		when(userService.createUser(any())).thenReturn(userMapper.fromDTO(baseUser));
		UserCreateDTO userReturn = userController.createUser(baseUser);
		verify(userService, times(1)).createUser(any());
		assertNotNull(userReturn);
		assertEquals(userReturn.getId(), ID);
		assertEquals(userReturn.getEmail(), EMAIL);
		assertNull(userReturn.getPhoneNumber());
		assertEquals(userReturn.getFirstName(), FNAME);
		assertEquals(userReturn.getLastName(), LNAME);
	}

	@Test
	public void testCreateUserWithInvalidEmailDomain() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setEmail("carlosjpantoja@outlook.com");
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}
	
	@Test
	public void testCreateUserWithInvalidEmail() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setEmail("carlos-j-pantoja@icesi.edu.co");
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}
	
	@Test
	public void testCreateUserWithInvalidPhoneNumberPrefix() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setPhoneNumber("+583135965423");
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}
	
	@Test
	public void testCreateUserWithInvalidPhoneNumberLength() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setPhoneNumber("+5731359654231");
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}
	
	@Test
	public void testCreateUserWithInvalidPhoneNumber() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setPhoneNumber("+573135965A23");
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}
	
	@Test
	public void testCreateUserWithoutEmailAndPhoneNumber() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setEmail(null);
			baseUser.setPhoneNumber(null);
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}
	
	@Test
	public void testCreateUserWithInvalidNameLengthSum() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setFirstName("Sixty charactersssssssssssssssssssssssssssssssssssssssssssss");
			baseUser.setLastName("Sixty one characterssssssssssssssssssssssssssssssssssssssssss");
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}
	
	@Test
	public void testCreateUserWithInvalidFirstName() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setFirstName("C@rlos");
			baseUser.setLastName("Pantoja");
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}
	
	@Test
	public void testCreateUserWithInvalidLastName() {
		try {
			UserCreateDTO baseUser = baseUser();
			baseUser.setFirstName("Carlos");
			baseUser.setLastName("Pant0ja");
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}

	@Test
	public void testCreateUserWithInvalidPassword() {
		try {
			UserCreateDTO baseUser = UserCreateDTO.builder().id(ID).password("password").build();
			userController.createUser(baseUser);
			fail();
		} catch (RuntimeException e) {
			verify(userService, times(0)).createUser(any());
		}
	}

	@Test
	public void testGetUsers() {
		UserCreateDTO baseUser = baseUser();
		when(userService.getUsers()).thenReturn(List.of(userMapper.fromDTO(baseUser)));
		List<UserDTO> usersResult = userController.getUsers();
		verify(userService, times(1)).getUsers();
		assertNotNull(usersResult.get(0));
		assertEquals(usersResult.get(0).getId(), ID);
		assertEquals(usersResult.get(0).getEmail(), EMAIL);
		assertEquals(usersResult.get(0).getPhoneNumber(), PHONE);
		assertEquals(usersResult.get(0).getFirstName(), FNAME);
		assertEquals(usersResult.get(0).getLastName(), LNAME);
	}

	private UserCreateDTO baseUser() {
		return UserCreateDTO.builder()
							.id(ID)
							.email(EMAIL)
							.phoneNumber(PHONE)
							.firstName(FNAME)
							.lastName(LNAME)
							.password(PASS)
							.build();
	}
	
}
