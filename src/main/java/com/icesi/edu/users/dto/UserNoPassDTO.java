package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNoPassDTO{
    private UUID userId;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

}



