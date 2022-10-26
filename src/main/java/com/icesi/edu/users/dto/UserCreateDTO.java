package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserCreateDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDateTime dateTime;

    private String password;

    public UserCreateDTO(){
        dateTime = LocalDateTime.now();
    }
}
