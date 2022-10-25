package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private UUID id;

    private String email;

    private String password;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String date;
}
