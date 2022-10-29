package com.icesi.edu.users.validation;

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

    @Documented
    @Constraint(validatedBy = NameValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface NameValidation{
        String message () default "The name can't contain special characters.";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }



    @Documented
    @Constraint(validatedBy = PhoneValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface PhoneValidation{
        String message () default "The phone doesn't fulfill the expected pattern";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}
