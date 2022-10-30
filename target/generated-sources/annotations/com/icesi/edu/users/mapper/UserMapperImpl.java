package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserPublicDTO;
import com.icesi.edu.users.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-30T10:18:47-0500",
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

        userDTO.setPassword( user.getHashedPassword() );
        userDTO.setId( user.getId() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPhoneNumber( user.getPhoneNumber() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setLastTimeSearched( user.getLastTimeSearched() );

        return userDTO;
    }

    @Override
    public UserPublicDTO fromPublicUser(User user) {
        if ( user == null ) {
            return null;
        }

        UserPublicDTO userPublicDTO = new UserPublicDTO();

        userPublicDTO.setId( user.getId() );
        userPublicDTO.setEmail( user.getEmail() );
        userPublicDTO.setPhoneNumber( user.getPhoneNumber() );
        userPublicDTO.setFirstName( user.getFirstName() );
        userPublicDTO.setLastName( user.getLastName() );
        userPublicDTO.setLastTimeSearched( user.getLastTimeSearched() );

        return userPublicDTO;
    }
}
