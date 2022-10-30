package com.icesi.edu.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.icesi.edu.users.validation.CustomAnnotations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    @Column(unique = true)
    @CustomAnnotations.EmailValidation
    private String email;

    @Column(unique = true)
    @CustomAnnotations.PhoneValidation
    private String phoneNumber;

    @CustomAnnotations.NameValidation
    private String firstName;

    @CustomAnnotations.NameValidation
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CustomAnnotations.PasswordValidation
    private String unhashedPassword;

    private LocalDate lastTimeSearched;

}
