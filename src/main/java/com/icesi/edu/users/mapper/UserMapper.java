package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

     @Mapping(source = "password", target = "hashed")
     User fromDTO(UserDTO userDTO);
     UserDTO fromUser(User user);
}
