package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.CustomAnnotations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private UUID id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    @CustomAnnotations.PasswordValidaton
    private String password;

}