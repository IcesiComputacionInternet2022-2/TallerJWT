package com.icesi.edu.users.mapper;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserDTOConsult;
import com.icesi.edu.users.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-30T11:00:52-0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromDTO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.email( userDTO.getEmail() );
        user.phoneNumber( userDTO.getPhoneNumber() );
        user.firstName( userDTO.getFirstName() );
        user.lastName( userDTO.getLastName() );
        user.password( userDTO.getPassword() );

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
        userDTO.setPassword( user.getPassword() );

        return userDTO;
    }

    @Override
    public UserDTOConsult fromUserToUserDTOConsult(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTOConsult userDTOConsult = new UserDTOConsult();

        userDTOConsult.setId( user.getId() );
        userDTOConsult.setEmail( user.getEmail() );
        userDTOConsult.setPhoneNumber( user.getPhoneNumber() );
        userDTOConsult.setFirstName( user.getFirstName() );
        userDTOConsult.setLastName( user.getLastName() );
        userDTOConsult.setPassword( user.getPassword() );

        return userDTOConsult;
    }
}
