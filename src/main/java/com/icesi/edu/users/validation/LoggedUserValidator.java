package com.icesi.edu.users.validation;

import com.icesi.edu.users.security.SecurityContextHolder;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.UUID;

public class LoggedUserValidator implements ConstraintValidator<CustomAnnotations.LoggedUserValidation, UUID> {

    @Override
    @SneakyThrows
    public boolean isValid(UUID userId, ConstraintValidatorContext constraintValidatorContext) {

        boolean isEquals = SecurityContextHolder.getContext().getUserId().equals(userId);

        if (!isEquals) throw new UserPrincipalNotFoundException("User cannot perform this action because it is not the logged user");

        return true;
    }
}
