package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.CustomAnnotations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    @CustomAnnotations.EmailValidation
    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDate lastTimeSearched;

    @CustomAnnotations.PasswordValidation
    private String unhashedPassword;
}
