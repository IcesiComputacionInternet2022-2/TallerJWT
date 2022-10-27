package com.icesi.edu.users.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.NamedInheritableThreadLocal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SecurityContextHolder {

    private static final String THREAD_NAME = "SECURITY_CONTEXT";
    private static final NamedInheritableThreadLocal<SecurityContext> CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(THREAD_NAME);

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    public static SecurityContext getContext() {
        SecurityContext context = CONTEXT_HOLDER.get();
        if (context == null) {
            context = createEmptyContext();
            CONTEXT_HOLDER.set(context);
        }
        return context;
    }

    private static SecurityContext createEmptyContext() {
        return new SecurityContext();
    }

    public static void setUserContext(SecurityContext context) {
        if (context != null) CONTEXT_HOLDER.set(context);
    }
}
