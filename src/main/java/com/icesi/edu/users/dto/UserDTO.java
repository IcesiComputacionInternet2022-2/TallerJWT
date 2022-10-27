package com.icesi.edu.users.dto;

import com.icesi.edu.users.validation.CustomAnnotation;
import com.icesi.edu.users.validation.CustomAnnotations;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    @NotBlank
    private String email;

    @NotNull
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

    private LocalDateTime dateCalled;

    public UserDTO(){
        dateCalled = LocalDateTime.now();
    }
}