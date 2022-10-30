package com.icesi.edu.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icesi.edu.users.validation.CustomAnnotations.NameValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    @NotBlank
    private String email;

    @NotNull
    private String phoneNumber;

    @Size(min = 1, max = 120)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 120)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate localDate;
}
