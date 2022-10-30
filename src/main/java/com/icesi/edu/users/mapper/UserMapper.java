package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserPublicDTO;
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


     @Mapping(source = "password", target = "hashedPassword")
     User fromDTO(UserDTO userDTO);

     @Mapping(source = "hashedPassword", target = "password")

     UserDTO fromUser(User user);

     UserPublicDTO fromPublicUser(User user);

}