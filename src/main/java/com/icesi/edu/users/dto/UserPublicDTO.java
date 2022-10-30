package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPublicDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;
    
    private String lastName;

    private LocalDate lastTimeSearched;


}