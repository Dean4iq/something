package ua.den.model.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ua.den.model.service.UserService;
import ua.den.model.validation.UniqueLogin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return (userService.getUserByLogin(login) == null);
    }
}
