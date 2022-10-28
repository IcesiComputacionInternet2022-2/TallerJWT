package com.icesi.edu.users.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Password doesn't meet the minimum requirements";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
