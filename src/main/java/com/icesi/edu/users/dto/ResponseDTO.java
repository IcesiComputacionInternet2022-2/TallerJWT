package com.icesi.edu.users.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseDTO {

    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String timeStamp;

    public ResponseDTO() {
        timeStamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
