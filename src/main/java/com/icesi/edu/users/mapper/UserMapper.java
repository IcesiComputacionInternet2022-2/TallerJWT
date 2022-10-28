package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserWithPasswordDTO;
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

     User fromDTO(UserDTO userDTO);

     User fromDTO(UserWithPasswordDTO userCreateDTO);

     UserDTO fromUser(User user);
     UserWithPasswordDTO fromUserWithPassword(User user);
}
