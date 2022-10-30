package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.CustomAnnotations.PasswordValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    private String email;

    @PasswordValidation
    private String password;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String callDate;

    public UserDTO() {
        callDate = String.valueOf(LocalDate.now());
    }
}