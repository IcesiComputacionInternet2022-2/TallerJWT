package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String date;

    public UserDTO(UUID uuid, String email, String phoneNumber, String firstName, String lastName) {
        this.id = uuid;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        date = "Not yet consulted";
    }
}
