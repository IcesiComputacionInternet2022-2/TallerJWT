package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-29T21:24:59-0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromDTO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.hashedPassword( userDTO.getPassword() );
        user.id( userDTO.getId() );
        user.email( userDTO.getEmail() );
        user.phoneNumber( userDTO.getPhoneNumber() );
        user.firstName( userDTO.getFirstName() );
        user.lastName( userDTO.getLastName() );
        user.lastTimeSearched( userDTO.getLastTimeSearched() );

        return user.build();
    }

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
        userDTO.setLastTimeSearched( user.getLastTimeSearched() );

        return userDTO;
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

        return user.build();
    }
}