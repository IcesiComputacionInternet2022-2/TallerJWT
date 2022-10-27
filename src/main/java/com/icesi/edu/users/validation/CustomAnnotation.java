package com.icesi.edu.users.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAnnotation {


    String message() default "Name is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}