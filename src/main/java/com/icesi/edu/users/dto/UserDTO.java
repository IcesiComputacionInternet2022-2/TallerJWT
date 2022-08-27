package com.icesi.edu.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String hiddenId;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date requestDate;

}
