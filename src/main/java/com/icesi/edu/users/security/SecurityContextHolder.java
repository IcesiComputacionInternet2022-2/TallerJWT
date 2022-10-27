package com.icesi.edu.users.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.NamedInheritableThreadLocal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SecurityContextHolder {

    private static final String THREAD_NAME = "SECURITY CONTEXT";
    private static final NamedInheritableThreadLocal<SecurityContext> CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(THREAD_NAME);

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }
    private static SecurityContext createEmptyContext() {
        return new SecurityContext();
    }

    public static SecurityContext getContext(){
        SecurityContext sc = CONTEXT_HOLDER.get();

        if(sc == null){
            sc = createEmptyContext();
            CONTEXT_HOLDER.set(sc);
        }

        return sc;
    }

    public static void setUserContext(SecurityContext context) {
        if(context != null)  CONTEXT_HOLDER.set(context);
    }
}
