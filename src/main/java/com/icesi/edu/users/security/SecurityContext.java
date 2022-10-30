package com.icesi.edu.users.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class SecurityContext implements Serializable {

    private static final long serialVersionUID = 4659821160803661194L;

    private UUID userId;
    private UUID roleId;
    private UUID organizationId;
    private String token;

}