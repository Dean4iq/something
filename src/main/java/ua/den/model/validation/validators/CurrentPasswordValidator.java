package ua.den.model.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.den.model.dto.UserPasswordDto;
import ua.den.model.entity.User;
import ua.den.model.service.UserService;
import ua.den.model.validation.DoesCurrentPasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrentPasswordValidator implements ConstraintValidator<DoesCurrentPasswordMatches, Object> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(Object dtoInstance, ConstraintValidatorContext constraintValidatorContext) {
        UserPasswordDto userPasswordDto = (UserPasswordDto) dtoInstance;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByLogin(userDetails.getUsername());

        return new BCryptPasswordEncoder(11).matches(userPasswordDto.getCurrentPassword(), user.getPassword());
    }
}
