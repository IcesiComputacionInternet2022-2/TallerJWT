package com.icesi.edu.users.security;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;
import java.util.UUID;

@Setter
@EqualsAndHashCode
@ToString
public class SecurityContext {
    private static final long serialVersionUID = 123476987923L;
    private UUID userId;
    private String token;

    public UUID getUserId(){
        return Optional.ofNullable(userId).orElseThrow();
    }

    public String getToken(){
        return token;
    }
}
