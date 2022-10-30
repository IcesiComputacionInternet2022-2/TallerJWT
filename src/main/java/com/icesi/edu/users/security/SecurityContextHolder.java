package com.icesi.edu.users.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.NamedInheritableThreadLocal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SecurityContextHolder {

    private static final String THREAD_NAME = "SECURITY_CONTEXT";
    private static final NamedInheritableThreadLocal<SecurityContext> contextHolder = new NamedInheritableThreadLocal<>(THREAD_NAME) ;

    /**
     * Remove the value of the actual context holder
     */
    public static void clearContext() {
        contextHolder.remove();
    }

    /**
     * Get the actual context, if it does not exist. create an empty one
     * @return {@link SecurityContext}
     */
    public static SecurityContext getContext() {
        SecurityContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }

    /**
     * Given a {@link SecurityContext context} update the actual context
     * @param context a {@link SecurityContext} instance
     */
    public static void setUserContext(SecurityContext context) {
        if (context != null) {
            contextHolder.set(context);
        }
    }

    public static SecurityContext createEmptyContext() {
        return new SecurityContext();
    }

}
