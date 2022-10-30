package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserWithoutPassword;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTOConsult;
import com.icesi.edu.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

     User fromDTO(UserDTO userDTO);

     /*@Mapping(source = "user.id", target = "id")
     @Mapping(source = "user.email", target = "email")
     @Mapping(source = "user.phoneNumber", target = "phoneNumber")
     @Mapping(source = "user.firstname", target = "firstname")
     @Mapping(source = "user.lastname", target = "lastname")
     @Mapping(target = "user.password" , ignore = true)
     UserWithoutPassword fromUserToUserWithoutPassword(User user);*/
     UserDTO fromUser(User user);
     UserDTOConsult fromUserToUserDTOConsult(User user);
}
