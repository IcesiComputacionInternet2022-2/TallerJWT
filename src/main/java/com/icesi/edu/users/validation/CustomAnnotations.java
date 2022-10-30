package com.icesi.edu.users.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

public class CustomAnnotations {

    @Documented
    @Constraint(validatedBy = NameValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NameValidation {


        String message() default "Name is invalid";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    @Documented
    @Constraint(validatedBy = EmailValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface EmailValidation {


        String message() default "Name is invalid";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }


    @Documented
    @Constraint(validatedBy = PasswordValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PasswordValidation{

        String message() default  "Password is invalid";

        Class<?>[] groups() default  {};

        Class<? extends  Payload>[] payload() default {};

    }

}
