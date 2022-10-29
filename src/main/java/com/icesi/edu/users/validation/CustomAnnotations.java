package com.icesi.edu.users.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

public interface CustomAnnotations {

    @Documented
    @Constraint(validatedBy = PasswordAnnotation.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface PasswordValidation{
        String message () default "Invalidate Password";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}
