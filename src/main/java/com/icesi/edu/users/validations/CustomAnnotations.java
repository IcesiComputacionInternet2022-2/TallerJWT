package com.icesi.edu.users.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


public interface CustomAnnotations {

    @Documented
    @Constraint(validatedBy = EmailValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ValidEmailAndDomain {
        String message () default "";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @Documented
    @Constraint(validatedBy = TelephoneValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ValidPhoneAndCode {
        String message () default "";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @Documented
    @Constraint(validatedBy = NameValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ValidName {
        String message () default "";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @Documented
    @Constraint(validatedBy = PasswordValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ValidPassword {
        String message () default "";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }
}
