package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.CustomAnnotations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private UUID id;

    @NotBlank
    @CustomAnnotations.EmailValidation
    @Column(unique = true)
    private String email;

    @NotNull
    @CustomAnnotations.PhoneValidation
    @Column(unique = true)
    private String phoneNumber;

    @Size(min = 1, max = 120)
    @CustomAnnotations.NameValidation
    private String firstName;

    @NotNull
    @CustomAnnotations.NameValidation
    @Size(min = 1, max = 120)
    private String lastName;

    @NotNull
    @CustomAnnotations.PasswordValidation
    private String password;

}