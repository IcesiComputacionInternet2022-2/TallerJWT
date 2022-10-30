package com.icesi.edu.users.security;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.NamedInheritableThreadLocal;

@NoArgsConstructor(access =  AccessLevel.PRIVATE)
public class SecurityContextHolder {
    private static final String THREAD_NAME = "SECURITY_CONTEXT";
    private static final NamedInheritableThreadLocal<SecurityContext> contextHolder  = new NamedInheritableThreadLocal<>(THREAD_NAME);


    public static void clearContext(){
        contextHolder.remove();
    }

    public static SecurityContext getContext(){
        SecurityContext ctx = contextHolder.get();
        if (ctx == null){
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }

    public static void setUserContext(SecurityContext context){
        if (context != null){
            contextHolder.set(context);
        }
    }

    public static SecurityContext createEmptyContext(){
        return new SecurityContext();
    }
}
