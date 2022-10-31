package com.icesi.edu.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    @Pattern(regexp = "[A-Za-z0-9._%+-]+@icesi+\\.+edu+\\.+co", message = "Email most be of the Icesi domain (@icesi.edu.co)")
    private String email;

    @Pattern(regexp = "^[+57]+[0-9]",message = "Phone should start with +57")
    private String phoneNumber;

    @Size(min = 1, max = 120, message = "First name can't be longer than 120 chars")
    private String firstName;

    @Size(min = 1, max = 120, message = "Last name can't be longer than 120 chars")
    private String lastName;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$%@])[A-Za-z\\d#$%@]+$")
    private String password;

    private Date date;
}
