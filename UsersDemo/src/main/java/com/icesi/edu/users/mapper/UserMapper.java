package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTORequest;
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

     User fromDTO(UserDTO userDTO);
     User fromCreateDTO(UserCreateDTO userCreateDTO);
     UserDTO fromUser(User user);
     UserDTORequest fromUserToDTORequest(User user);
     UserCreateDTO fromUserToCreateDTO(User user);

     default String fromUUID(UUID uuid) {
          return uuid.toString();
     }

     default UUID fromUUID(String uuid) {
          return UUID.fromString(uuid);
     }
}
