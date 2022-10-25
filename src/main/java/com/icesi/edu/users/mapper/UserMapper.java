package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserWithParentsDTO;
import com.icesi.edu.users.model.User;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

     User fromDTO(UserDTO userDTO);
     UserDTO fromUser(User user);

     @Mapping(source = "child.id", target = "id")
     @Mapping(source = "child.name", target= "name")
     @Mapping(source = "parent", target = "padre")
     @Mapping(source = "mother", target = "madre")
     UserWithParentsDTO fromUsers(User child, User parent, User mother);

     default String fromUUID(UUID uuid){
          return uuid.toString();
     }

     default UUID fromUUID(String uuid){
          return UUID.fromString(uuid);
     }
}
