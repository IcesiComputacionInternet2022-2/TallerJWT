package com.icesi.edu.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.icesi.edu.users.validations.CustomAnnotations;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    @CustomAnnotations.ValidEmailAndDomain
    private String email;

    @CustomAnnotations.ValidPhoneAndCode
    private String phoneNumber;

    @CustomAnnotations.ValidName
    private String firstName;

    @CustomAnnotations.ValidName
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CustomAnnotations.ValidPassword
    private String password;

    private LocalDateTime modifiedTime;

    public UserDTO() {
        modifiedTime = LocalDateTime.now();
    }
}
