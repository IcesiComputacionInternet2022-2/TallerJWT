package com.icesi.edu.users.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

public interface CustomAnnotations {

    @Documented
    @Constraint(validatedBy = NameValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface NameValidation {


        String message() default "Name is invalid";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    @Documented
    @Constraint(validatedBy = NameValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface EmailValidation {


        String message() default "Name is invalid";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    @Documented
    @Constraint(validatedBy = PasswordValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface PasswordValidation {

        String message() default "Password is invalid";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    @Documented
    @Constraint(validatedBy = LoggedUserValidator.class)
    @Target({ ElementType.METHOD, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @interface LoggedUserValidation {

        String message() default "User is not logged";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }
}




