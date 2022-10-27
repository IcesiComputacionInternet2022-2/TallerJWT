package com.icesi.edu.users.validation;

import com.icesi.edu.users.error.GlobalExceptionHandler;
import com.icesi.edu.users.error.exception.UserException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

public interface CustomAnnotations {

    @Documented
    @Constraint(validatedBy = PasswordValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface PasswordValidation{
        String message () default "The password doesn't fulfill the minimum character diversity";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    @Documented
    @Constraint(validatedBy = EmailValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface EmailValidation{
        String message () default "The email doesn't fulfill the expected pattern";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}
