package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.CustomAnnotations;
import com.icesi.edu.users.validation.CustomAnnotations.NameValidation;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private UUID id;

    @NotBlank
    private String email;

    @com.sun.istack.NotNull
    private String phoneNumber;

    @Size(min = 1, max = 120)
    @CustomAnnotations.NameValidation
    private String firstName;

    @NotNull
    @Size(min = 1, max = 120)
    private String lastName;


    @Size(min = 8,  max=30)
    @CustomAnnotations.PasswordValidation
    private String password;

}