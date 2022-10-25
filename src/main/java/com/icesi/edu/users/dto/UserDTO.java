package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
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

    private LocalDate lastTimeSearched;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W])(?!.*[\\s])")
    private String unhashedPassword;
}
