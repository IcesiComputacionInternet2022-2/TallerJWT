package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.ResponseDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTests {

    private UserMapper userMapper;

    @BeforeEach
    public void init() {
        userMapper = new UserMapperImpl();
    }

    @Test
    public void testFromDTONull() {
        assertNull(userMapper.fromDTO(null));
    }

    @Test
    public void testFromDTO() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test", "test");
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        assertEquals(user, userMapper.fromDTO(userDTO));
    }


    @Test
    public void testFromUserNull() {
        assertNull(userMapper.fromUser(null));
    }

    @Test
    public void testToResponseNull() {
        assertNull(userMapper.toResponse(null));
    }

    @Test
    public void testToResponse() {
        UserDTO userDTO = new UserDTO(null, "test@icesi.edu.co", "+573101231234", "test", "test");
        User user = userMapper.fromDTO(userDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setId(user.getId());
        assertEquals(responseDTO, userMapper.toResponse(user));
    }
}
