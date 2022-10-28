package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserWithPasswordDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDateTime dateTime;

    private String password;

    public UserWithPasswordDTO(){
        dateTime = LocalDateTime.now();
    }
}
