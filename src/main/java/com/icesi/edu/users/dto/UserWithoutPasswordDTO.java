package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserWithoutPasswordDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String callDate;

    public UserWithoutPasswordDTO() {
        callDate = String.valueOf(LocalDate.now());
    }
}