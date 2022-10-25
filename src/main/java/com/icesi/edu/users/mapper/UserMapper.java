package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

     User fromDTO(UserDTO userDTO);
     UserDTO fromUser(User user);
}
