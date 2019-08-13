package ua.den.restful.model.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ua.den.restful.model.service.UserService;
import ua.den.restful.model.validation.UniqueLogin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return  (userService.getUserByLogin(login) == null);
    }
}
