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

    @CustomAnnotations.PhoneValidation
    private String phoneNumber;

    @CustomAnnotations.NameValidation
    private String firstName;

    @CustomAnnotations.NameValidation
    private String lastName;

    private LocalDate lastTimeSearched;

    @CustomAnnotations.PasswordValidation
    private String unhashedPassword;
}
