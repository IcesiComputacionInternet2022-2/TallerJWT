package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.CustomAnnotations.PasswordValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    @NotNull
    @PasswordValidation
    private String password;
}
