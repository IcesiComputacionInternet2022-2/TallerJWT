package com.icesi.edu.users.validation;

import com.icesi.edu.users.constant.UserErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

public interface CustomAnnotations {

    @Documented
    @Constraint(validatedBy = PasswordValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface PasswordValidation {

        String message() default UserErrorCode.CODE_UD_06;

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}