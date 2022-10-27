package com.icesi.edu.users.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class LoginDTO {
    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDateTime modifiedTime;

    public LoginDTO() {
        modifiedTime = LocalDateTime.now();
    }
}
