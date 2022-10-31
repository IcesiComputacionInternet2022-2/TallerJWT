package com.icesi.edu.users.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;
}
