package com.icesi.edu.users.security;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode
@ToString
public class SecurityContext implements Serializable {

    @Getter(AccessLevel.NONE)
    private static final long SERIAL_VERSION_UID = 1;
    private UUID userId;
    private UUID roleId;
    private UUID organizationId;
    private String token;
}
