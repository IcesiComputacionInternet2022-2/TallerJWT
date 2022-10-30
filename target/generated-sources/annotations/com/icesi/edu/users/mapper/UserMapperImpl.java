package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-30T16:32:54-0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO fromUser(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );

        return userDTO;
    }

    @Override
    public UserCreateDTO CreateUser(User user) {
        if ( user == null ) {
            return null;
        }

        UserCreateDTO userCreateDTO = new UserCreateDTO();

        userCreateDTO.setId( user.getId() );
        userCreateDTO.setPhoneNumber( user.getPhoneNumber() );
        userCreateDTO.setEmail( user.getEmail() );
        userCreateDTO.setFirstName( user.getFirstName() );
        userCreateDTO.setLastName( user.getLastName() );
        userCreateDTO.setPassword( user.getPassword() );

        return userCreateDTO;
    }

    @Override
    public User fromDTO(UserCreateDTO userCreateDTO) {
        if ( userCreateDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userCreateDTO.getId() );
        user.email( userCreateDTO.getEmail() );
        user.phoneNumber( userCreateDTO.getPhoneNumber() );
        user.firstName( userCreateDTO.getFirstName() );
        user.lastName( userCreateDTO.getLastName() );
        user.password( userCreateDTO.getPassword() );

        return user.build();
    }
}
