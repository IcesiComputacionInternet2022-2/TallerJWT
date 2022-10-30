package com.icesi.edu.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icesi.edu.users.validation.CustomAnnotations;
import lombok.*;

import javax.validation.Valid;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    @CustomAnnotations.PasswordValidation
    private String password;
}
