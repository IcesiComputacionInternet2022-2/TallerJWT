package validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<CustomAnnotations.PasswordValidation, String> {

    private final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=(.*[a-z]))(?=(.*[\\d]))(?=(.*[#$%@]))(?=.*[\\W]).{4,}$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(s + ":" + s.matches(PASSWORD_REGEX));
        return s.matches(PASSWORD_REGEX);
    }

}
